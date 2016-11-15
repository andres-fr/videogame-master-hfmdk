package com.mygdx.game;



import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.mygdx.game.actors.Player;
import com.mygdx.game.core.AssetsManager;
import com.mygdx.game.core.GameActions;
import com.mygdx.game.core.GameScreenUI;
import com.mygdx.game.screens.lobby.MainMenuScreen;
import com.mygdx.game.screens.lobby.PauseMenuScreen;
import com.mygdx.game.screens.lobby.PresentationScreen;
import com.mygdx.game.supercollider.SimpleJColliderClient;
import com.mygdx.game.supercollider.SimpleScalaColliderClient;
import com.mygdx.game.supercollider.SupercolliderClient;


public class MyGame extends Game {
    public final static int WIDTH = 1280;
    public final static int HEIGHT = 720;
    public final static boolean DEBUG = false ;
    public final static boolean FULLSCREEN = !DEBUG;
    public final static String VERSION = "0.0";
    public final static float CAM_SPEED_RATIO = 1f/200f; // to be multiplied by player speed
    // game related instances
    public AssetsManager assetsManager;
    public PolygonSpriteBatch batch;
    public Player player;
    public GameActions actions;
    // a pointer to the pause menu
    private PauseMenuScreen pauseMenu = null;
    // a pointer to the currently active screen
    private GameScreenUI currentScreen = null;
    // the client for the SuperCollider server
    private SupercolliderClient scClient;
    //private SimpleScalaColliderClient scClient;


	@Override
	public void create () {
        if (FULLSCREEN) Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        assetsManager = new AssetsManager();
        batch = new PolygonSpriteBatch();
        actions = new GameActions(this);
        player = new Player(this);
        pauseMenu = new PauseMenuScreen(this);
        //scClient = new SimpleJColliderClient();
        scClient = new SimpleScalaColliderClient();

        if (DEBUG==false) {
            assetsManager.prepare(AssetsManager.PREPARE.LOBBY);
            currentScreen = new PresentationScreen(this);//new PresentationScreen(this);

        } else { // DEBUG==true
            assetsManager.prepare(AssetsManager.PREPARE.LOBBY);
            currentScreen = new MainMenuScreen(this);
        }
        // start game!
        setScreenINSECURE(currentScreen, "imSureOfWhatImDoing");


        /*
        if (!DEBUG) {
            actions.gotoNewScreen(PresentationScreen.class, new Object[]{this}, 0, 0);
        } else{
            actions.gotoNewScreen(RoomChapter1Screen.class, new Object[]{this}, 0, 0);
        }

        */
    }

    /**
     * this method sets the currentScreen variable, and activates setScreen(), but it has an extra boolean flag,
     * for atibugging purposes:
     * every setScreen call should be done through the gotoScreen method in the GameActions, so the vanilla
     * setScreen is overriden to throw an exception when used
     * @param screen the new Screen to be set
     * @param areUSureOfWhatUrDoing this must equal 'imSureOfWhatImDoing' if you want this method to perform
     */
    public void setScreenINSECURE(GameScreenUI screen, String areUSureOfWhatUrDoing) {
        if (areUSureOfWhatUrDoing.equals("imSureOfWhatImDoing")) {
            currentScreen = screen;
            super.setScreen(screen);
        } else throw new RuntimeException("ANTIBUGGING: SetScreenSecure is public, but not expected to be directly used "+
        "(See GameActions.java). If you still want to use it, type 'imSureOfWhatImDoing' at the String field.");
    }

    /**
     * this method is overriden and set to throw an exception, use gotoScreen instead
     * @param screen
     */
    @Override
    public void setScreen(Screen screen) {
        throw new RuntimeException("ANTIBUGGING: setScreen() not allowed! use GameActions.goto...() instead");
    }

    public GameScreenUI getCurrentScreen() {
        return currentScreen;
    }

    public PauseMenuScreen getPauseMenu() {
        return pauseMenu;
    }

    public SupercolliderClient getScClient() {
        return scClient;
    }

    @Override
    public void dispose() {
        super.dispose();
        scClient.close();
        assetsManager.dispose();
        batch.dispose();
        currentScreen.dispose();
    }

    /**
     * this is an alias for Gdx.app.exit()
     * but may contain some dispose calls in the future?
     */
    public void exit() {
        Gdx.app.exit();

    }
}