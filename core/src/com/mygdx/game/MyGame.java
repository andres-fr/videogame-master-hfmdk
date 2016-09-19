package com.mygdx.game;



import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.mygdx.game.actors.Player;
import com.mygdx.game.core.AssetsManager;
import com.mygdx.game.core.GameActions;
import com.mygdx.game.core.GameScreenUI;
import com.mygdx.game.screens.lobby.MainMenuScreen;
import com.mygdx.game.screens.lobby.PresentationScreen;

public class MyGame extends Game {
    public final static int WIDTH = 1280;
    public final static int HEIGHT = 720;
    public final static boolean DEBUG = false;
    public final static boolean FULLSCREEN = !DEBUG;
    public final static String VERSION = "0.0";
    public final static int PLAYER_SPEED = 200; // in pixels per second
    public final static float CAM_SPEED = PLAYER_SPEED/200; // proportional to player speed
    // game related instances
    public AssetsManager assetsManager;
    public PolygonSpriteBatch batch;
    public Player player;
    // a pointer to the currently active screen
    private GameScreenUI currentScreen = null;

	@Override
	public void create () {
        if (FULLSCREEN) Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        assetsManager = new AssetsManager();
        batch = new PolygonSpriteBatch();

        player = new Player(this, true, 0);

        if (true) { // write false to configure init game in another point
            assetsManager.prepare(AssetsManager.PREPARE.LOBBY);
            currentScreen = new PresentationScreen(this);//new PresentationScreen(this);
        } else {
            assetsManager.prepare(AssetsManager.PREPARE.LOBBY);
            currentScreen = new MainMenuScreen(this);
        }
        // start game!
        setScreenSECURE(currentScreen, true);
    }


    /**
     * This method is the official way to change from one GameScreen to another.
     * It uses reflection mainly because of the following reasons:
     * 1) it was important to call  "assetsManager.prepare" BEFORE instantiating the GameScreen in EVERY case
     * 2) the alternative of passing a GameScreenUI as an argument would cause it to be instantiated before the method starts
     * 3) an if-else design would be a huge overhead, since there are lots of classes that extend GameScreenUI. That's what
     * reflection is been made for. Usage example, to be found in MainMenuScreen.java:
     * game.gotoScreen(StreetChapter1Screen.class, new Object[]{game}, AssetsManager.PREPARE.CHAPTER1, 0, 0.5f);
     * @param screenClass the class of the string to be switched to.
     * @param parameters an Object array holding the clazz's constructor parameters, respecting the order
     * @param prepareAsset the AssetsManager.PREPARE element to be "prepared" before screen instantiation
     * @param fadeout the fadeOut time before screen switching, in seconds (it can be zero)
     * @param fadein the fadeIn time after screen switching, in seconds (it can be zero)
     * @return an Action consisting of fadeOut->prepareAsset->changeScreen->fadein
     */
    public Action gotoScreenREFLECTED(final Class<? extends GameScreenUI> screenClass, final Object[] parameters,
                             final AssetsManager.PREPARE prepareAsset, final float fadeout, final float fadein) {
        final Class[] parameterClasses = new Class[parameters.length];
        for (int i= 0; i<parameters.length; i++){
            parameterClasses[i] = parameters[i].getClass();
        }
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    assetsManager.prepare(prepareAsset);
                    Constructor cons = ClassReflection.getConstructor(screenClass, parameterClasses);
                    GameScreenUI gs = (GameScreenUI) cons.newInstance(parameters);
                    if (currentScreen != null) currentScreen.dispose();
                    currentScreen = gs;
                    setScreenSECURE(currentScreen, true);
                    gs.stage.addAction(GameActions.fadeOutFadeIn(0, fadein));
                } catch (ReflectionException e) {
                    e.printStackTrace();
                }
            }
        }; return GameActions.fadeOutRunFadeIn(fadeout, r, 0);
    }


    /**
     * this version of gotoScreen is useful between screens that share the same assets, but
     * fatal else! it is implemented because gotoScreen forces screen instantiation, which can
     * become CPU-expensive if constantly switching between screens that share the same asset.
     * Usage example, to be found in PresentationScreen.java:
     * game.gotoScreenWITHOUTPREPARING(new MainMenuScreen(game), 0, 0.5f)
     * @param gs the reference of the GameScreen to be switched to
     * @param fadeout the fadeOut time before switching, in seconds (it can be zero)
     * @param fadein the fadeIn time after switching, in seconds (it can be zero)
     * @param disposeCurrent if true&&currentScreen!=null, the currentScreen is disposed before currentScreen=gs
     * @return an Action consisting of fadeOut->changeScreen->fadein
     */
    public Action gotoScreenWITHOUTPREPARING(final GameScreenUI gs, float fadeout, final float fadein, final boolean disposeCurrent) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                if (disposeCurrent && currentScreen!=null) currentScreen.dispose();
                currentScreen = gs;  //clazz.getDeclaredConstructor(parameter.getClass()).newInstance(parameter);
                setScreenSECURE(gs, true);
                gs.stage.addAction(GameActions.fadeOutFadeIn(0, fadein));
            }
        };
        return GameActions.fadeOutRunFadeIn(fadeout, r, 0);
    }


    /**
     * this method performs the same as setScreen but with an extra boolean flag, for atibugging purposes:
     * every setScreen call should be done through the gotoScreen method in this class, so the vanilla
     * setScreen is overriden to throw an exception when used
     * @param screen
     * @param check
     */
    public void setScreenSECURE(Screen screen, boolean check) {
        super.setScreen(screen);
    }

    /**
     * this method is overriden and set to throw an exception, use gotoScreen instead
     * @param screen
     */
    @Override
    public void setScreen(Screen screen) {
        throw new RuntimeException("setScreen() not allowed! use MyGame.gotoScreen() instead");
    }

    public GameScreenUI getCurrentScreen() {
        return currentScreen;
    }



    @Override
    public void dispose() {
        super.dispose();
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