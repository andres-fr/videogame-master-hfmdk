package com.mygdx.game.supercollider;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import de.sciss.jcollider.Constants;
import de.sciss.jcollider.GraphElem;
import de.sciss.jcollider.Group;
import de.sciss.jcollider.JCollider;
import de.sciss.jcollider.NodeWatcher;
import de.sciss.jcollider.Server;
import de.sciss.jcollider.ServerEvent;
import de.sciss.jcollider.ServerListener;
import de.sciss.jcollider.Synth;
import de.sciss.jcollider.SynthDef;
import de.sciss.jcollider.UGen;
import de.sciss.jcollider.UGenInfo;

/**
 * Created by afr on 25.09.16.
 */


/**
 * this class tests the functionality of JCollider at its most basic level, and it is
 * basically a version of JCollider's demo
 * https://github.com/Sciss/JCollider/blob/master/src/test/java/de/sciss/jcollider/test/Demo.java
 * without GUIs and Demo-Defsynths. After instantiation, when the server is connected,
 * up and running, a call of the "playTest()" method should output a SinOsc.ar([440, 470])
 * to the two lowest-numbered channels.
 * If talked too soon after booting, it may ignore the first OSC messages
 * even if server.isRunning() returns true and the first ServerEvent.RUNNING
 * has been received (in my case it is about 3 seconds, but probably because of the JACK
 * reconnections).
 *
 * As the rest of this project it is licensed under GPLv3
 */
public class SimpleSCClient implements ServerListener, Constants {

    protected Server server = null;
    protected NodeWatcher nw = null;
    protected Group grpAll;
    protected SynthDef testSynth;

    public SimpleSCClient() {
        final String fs	= File.separator;

        try {
            // create supercollider server representation, load current ugendefs, define new test synth (a simple SinOsc)
            server = new Server("localhost");
            UGenInfo.readBinaryDefinitions();

            // SynthDef.new("testSynth", {Out.ar(0, (SinOsc.ar([440, 470])*0.04))})
            GraphElem sinosc = UGen.ar("*", UGen.ar("SinOsc", UGen.array(UGen.ir(440), UGen.ir(470))), UGen.ir(0.1f));
            testSynth = new SynthDef( "testSynth", UGen.ar( "Out", UGen.ir( 0 ), sinosc ));

            // find the scsynth app, and set it to this.server representation
            File f = findFile( JCollider.isWindows ? "scsynth.exe" : "scsynth", new String[] {
                    fs + "Applications" + fs + "SuperCollider_f",
                    fs + "Applications" + fs + "SC3",
                    fs + "usr" + fs + "local" + fs + "bin",
                    fs + "usr" + fs + "bin",
                    "C:\\Program Files\\SC3",
                    "C:\\Program Files\\SuperCollider_f"
            });
            if( f != null ) Server.setProgram( f.getAbsolutePath() );

            // allow further interaction with the server (override behaviour in the serverAction method)
            server.addListener( this );
            try {
                server.start();
                server.startAliveThread();
                server.boot();
            }
            catch( IOException e1 ) { /* ignored */ }
        }
        // finish constructor with catch in case something went wrong
        catch( IOException e2 ) {
            System.out.println("Failed to create the SC client: ");
            reportError(e2);
        }
    }


    // ------------- ServerListener interface --------------
    public void serverAction( ServerEvent sEvt ) { // RUNNING=0, STOPPED=1, COUNTS=2
        switch( sEvt.getID() ) {
            case ServerEvent.COUNTS:
                performActionByServerCount(sEvt);
                break;
            case ServerEvent.RUNNING: // called once when server is up and running
                try {
                    server.dumpOSC();
                    initServer();
                }
                catch( IOException e) {
                    reportError(e);
                }
                break;

            case ServerEvent.STOPPED: // called once when server stops
                // re-run alive thread
                final javax.swing.Timer t = new javax.swing.Timer( 1000, new ActionListener() {
                    public void actionPerformed( ActionEvent aEvt )
                    {
                        try {
                            if( server != null ) server.startAliveThread();
                        }
                        catch( IOException e ) {
                            reportError(e);
                        }
                    }
                });
                t.setRepeats( false );
                t.start();
                break;

            default:
                break;
        }
    }

    /**
     * this method is called when the server sends the ServerEvent.RUNNING message
     * (once at the beginning)
     * @throws IOException
     */
    private void initServer() throws IOException {
        try {
            testSynth.send(server);
        } catch (IOException e) {
            System.err.println( "Error sending def " + testSynth.getName() + " : " +
                    e.getClass().getName() + " : " + e.getLocalizedMessage() );
        }
        if( !server.didWeBootTheServer() ) {
            server.initTree();
            server.notify( true );
        }
        nw	= NodeWatcher.newFrom(server);
        grpAll = Group.basicNew( server ); // creates the group in the client-side
        nw.register( server.getDefaultGroup() );
        nw.register( grpAll );
        server.sendMsg( grpAll.newMsg() ); // creates the group in the server-side
    }

    // finally some helping methods
    private static void reportError( Exception e ) {
        System.err.println( e.getClass().getName() + " : " + e.getLocalizedMessage() );
    }
    private static File findFile( String fileName, String[] folders )
    {
        File f;
        for( int i = 0; i < folders.length; i++ ) {
            f = new File( folders[ i ], fileName );
            if( f.exists() ) return f;
        }
        return null;
    }



    //////////////////////////////////////////////////////////////////////////
    ////////// PROPER CLIENT FUNCTIONALITY
    //////////////////////////////////////////////////////////////////////////


    /**
     * this method can be overriden to perform any action each time the server
     * sends a COUNT Event (about 2-3 per second)?
     */
    public void performActionByServerCount(ServerEvent e) {

    }

    /**
     * call this method after the server is up and running to output
     * a SinOsc.ar([440, 470]) to the two lowest-numbered channels.
     * Call stopAll() when you wish to stop it.
     */
    public void playTest() {
        final SynthDef def = testSynth;
        final Synth	synth;
        if( (def != null) && (grpAll != null) && (server != null) ) {
            try {
                synth = Synth.basicNew(def.getName(), server);
                if (nw != null) nw.register(synth);
                server.sendMsg(synth.newMsg(grpAll)); // adds the synth to the group
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * stops all synths, it calls freeAll() from the main synth group.
     */
    public void stopAll() {
        if( grpAll != null ) {
            try {grpAll.freeAll();}
            catch( IOException e ) {e.printStackTrace();}
        }
    }

    /**
     * this should take care of all garbage
     */
    public void close() {
        stopAll();
        if( nw != null ) {
            nw.dispose();
            nw = null;
        }
        if( server != null ) {
            try {
                if( server.didWeBootTheServer() ){
                    server.quitAndWait();
                }
                else if( grpAll != null ) grpAll.free();
                server = null;
            }
            catch( IOException e ) {
                reportError(e);
            }
        }
    }
}
