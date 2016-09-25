package com.mygdx.game.supercollider;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Comparator;

import de.sciss.jcollider.*;

/**
 * Created by afr on 25.09.16.
 */

public class Test implements FileFilter, ServerListener, Constants {

    protected static final Comparator synthDefNameComp = new SynthDefNameComp();

    protected Server		server	= null;
    protected NodeWatcher	nw		= null;
    protected Group			grpAll;

    public Test() {

        try {
            server = new Server( "localhost" );
//			loadDefs();
            //createDefs();
            // this was createdefs:
            try {
//			UGenInfo.readDefinitions();
                UGenInfo.readBinaryDefinitions();

                final List collDefs = DemoDefs.create();
                Collections.sort( collDefs, synthDefNameComp );
//			defTables[ 1 ].addDefs( collDefs );
                defTables[ 0 ].addDefs( collDefs );
            }
            catch( IOException e1 ) {
                e1.printStackTrace();
//			reportError( e1 );
            }

            File f = findFile( JCollider.isWindows ? "scsynth.exe" : "scsynth", new String[] {
                    fs + "Applications" + fs + "SuperCollider_f",
                    fs + "Applications" + fs + "SC3",
                    fs + "usr" + fs + "local" + fs + "bin",
                    fs + "usr" + fs + "bin",
                    "C:\\Program Files\\SC3",
                    "C:\\Program Files\\SuperCollider_f"
            });
//			if( (f == null) && JCollider.isMacOS ) {
//				try {
//					f = MRJAdapter.findApplication( "SCjm" );
//					if( f != null ) f = new File( f.getParentFile(), "scsynth" );
//				}
//				catch( IOException e1 ) {}
//			}
            if( f != null ) Server.setProgram( f.getAbsolutePath() );

            ggAppPath.setText( Server.getProgram() );
            ggAppPath.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e )
                {
                    Server.setProgram( ggAppPath.getText() );
                }
            });
            lb = new JLabel( "Server App Path :" );
            lb.setBorder( BorderFactory.createEmptyBorder( 2, 6, 2, 4 ));
            b2.add( lb );
            b2.add( ggAppPath );
            cp.add( b2, BorderLayout.NORTH );
            cp.add( createButtons(), BorderLayout.SOUTH );

            server.addListener( this );
            try {
                server.start();
                server.startAliveThread();
            }
            catch( IOException e1 ) { /* ignored */ }
//			if( server.isRunning() ) initServer();
            spf = ServerPanel.makeWindow( server, ServerPanel.MIMIC | ServerPanel.CONSOLE | ServerPanel.DUMP );
        }
        catch( IOException e1 ) {
            JOptionPane.showMessageDialog( this, "Failed to create a server :\n" + e1.getClass().getName() +
                    e1.getLocalizedMessage(), this.getTitle(), JOptionPane.ERROR_MESSAGE );
        }

        JCollider.setDeepFont( cp, fntGUI );

        addWindowListener( new WindowAdapter() {
            public void windowClosing( WindowEvent e )
            {
                if( nw != null ) {
                    nw.dispose();
                    nw = null;
                }
                if( server != null ) {
                    try {
                        if( server.didWeBootTheServer() ) server.quitAndWait();
                        else if( grpAll != null ) grpAll.free();
                        server = null;
                    }
                    catch( IOException e1 ) {
                        reportError( e1 );
                    }
                }
                setVisible( false );
                dispose();
                System.exit( 0 );
            }
        });

        setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );

//		pack();
        if( spf != null ) setLocation( spf.getX() + spf.getWidth() + 24, spf.getY() );
        setSize( 512, 512 );
        setVisible( true );
        toFront();
    }


    }
























    // ------------- ServerListener interface -------------
    public void serverAction( ServerEvent e )
    {
        switch( e.getID() ) {
            case ServerEvent.RUNNING:
                try {
                    initServer();
                }
                catch( IOException exception) {
                    reportError(exception);
                }
                break;

            case ServerEvent.STOPPED:
                // re-run alive thread
                final javax.swing.Timer t = new javax.swing.Timer( 1000, new ActionListener() {
                    public void actionPerformed( ActionEvent e )
                    {
                        try {
                            if( server != null ) server.startAliveThread();
                        }
                        catch( IOException exception ) {
                            reportError(exception);
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
    public boolean accept( File f )
    {
        try {
            return SynthDef.isDefFile( f );
        }
        catch( IOException e1 ) {
            return false;
        }
    }




    private void initServer() throws IOException {
        sendDefs();
        if( !server.didWeBootTheServer() ) {
            server.initTree();
            server.notify( true );
        }
        //		if( nw != null ) nw.dispose();
        nw		= NodeWatcher.newFrom( server );
        grpAll	= Group.basicNew( server );
        nw.register( server.getDefaultGroup() );
        nw.register( grpAll );
        server.sendMsg( grpAll.newMsg() );
    }


    protected static void reportError( Exception e ) {
        System.err.println( e.getClass().getName() + " : " + e.getLocalizedMessage() );
    }
}
