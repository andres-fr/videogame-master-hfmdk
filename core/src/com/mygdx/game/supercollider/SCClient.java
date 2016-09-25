package com.mygdx.game.supercollider;

import com.android.org.conscrypt.SSLClientSessionCache;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import javax.swing.JFrame;

import de.sciss.jcollider.*;
import de.sciss.jcollider.gui.ServerPanel;
import de.sciss.net.OSCMessage;

/**
 * Created by afr on 25.09.16.
 */

public class SCClient implements FileFilter, ServerListener, Constants {

    public Server		server	= null;
    protected NodeWatcher	nw		= null;
    protected Group grpAll;
    protected SynthDef testSynth;

    JFrame serverPannel;

    public SCClient() {
        final String fs	= File.separator;

        try {
            // create supercollider server representation, load current ugendefs, define new test synth (a simple SinOsc)
            server = new Server( "localhost" );
            UGenInfo.readBinaryDefinitions();
            testSynth = new SynthDef("testSynth", UGen.ar( "*", UGen.ar( "SinOsc", UGen.ir(440), UGen.ir( 0 )), UGen.ir( 0.04f )));

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

            server.addListener( this );
            try {
                server.start();
                server.startAliveThread();
                //server.boot();
            }
            catch( IOException e1 ) { /* ignored */ }
            serverPannel = ServerPanel.makeWindow( server, ServerPanel.MIMIC | ServerPanel.CONSOLE | ServerPanel.DUMP );
        }
        catch( IOException e ) {
            System.out.println("Failed to create a server: ");
            reportError(e);
        }
    }

    private void initServer() throws IOException {
        try {
            testSynth.send(server);
        } catch (IOException e) {
            System.err.println( "Sending Def " + testSynth.getName() + " : " +
                    e.getClass().getName() + " : " + e.getLocalizedMessage() );
        }
        if( !server.didWeBootTheServer() ) {
            server.initTree();
            server.notify( true );
        }
        nw	= NodeWatcher.newFrom( server );
        grpAll = Group.basicNew( server );
        nw.register( server.getDefaultGroup() );
        nw.register( grpAll );
        server.sendMsg( grpAll.newMsg() );
    }



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


    // ------------- ServerListener interface -------------
    public void serverAction( ServerEvent sEvt ) { // RUNNING=0, STOPPED=1, COUNTS=2
        switch( sEvt.getID() ) {
            case ServerEvent.RUNNING:
                try {
                    server.dumpOSC();
                    initServer();
                }
                catch( IOException e) {
                    reportError(e);
                }
                break;

            case ServerEvent.STOPPED:
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


    // ------------- FileFilter interface -------------
    public boolean accept( File f ) {
        try {
            return SynthDef.isDefFile( f );
        }
        catch( IOException e1 ) {
            return false;
        }
    }


    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////


    public void playTest() {
        final SynthDef def = testSynth;
        final Synth	synth;
        if( (def != null) && (grpAll != null) && (server != null) ) {
            try {
                synth = Synth.basicNew(def.getName(), server);
                if (nw != null) nw.register(synth);
                server.sendMsg(synth.newMsg(grpAll));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopAll() {
        if( grpAll != null ) {
            try {
                grpAll.freeAll();
            }
            catch( IOException e ) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        stopAll();

        if( nw != null ) {
            nw.dispose();
            nw = null;
        }
        if( serverPannel != null ) {
            serverPannel.dispose();
            serverPannel = null;
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
