package com.mygdx.game;



import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.game.actors.Player;
import com.mygdx.game.core.AssetsManager;
import com.mygdx.game.core.GameActions;
import com.mygdx.game.core.GameScreenUI;
import com.mygdx.game.screens.chapter1.StreetChapter1Screen;
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
            assetsManager.prepare("lobby");
            currentScreen = new PresentationScreen(this);
        } else {
            assetsManager.prepare("chapter1");
            currentScreen = new StreetChapter1Screen(this);
        }
        // start game!
        setScreenSECURE(currentScreen, true);
    }

    @Override
    public void dispose() {
        super.dispose();
        assetsManager.dispose();
        batch.dispose();
    }




    public Action gotoScreen(final GameScreenUI gs, final String prepareAsset, float fadeout, final float fadein) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                assetsManager.prepare(prepareAsset);
                setScreenSECURE(gs, true);
                if (currentScreen != null) currentScreen.dispose();
                currentScreen = gs;
                GameActions.fadeOutFadeIn(0, fadein);
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
    private void setScreenSECURE(Screen screen, boolean check) {
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
}