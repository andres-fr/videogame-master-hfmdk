package com.mygdx.game;



import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.mygdx.game.actors.Player;
import com.mygdx.game.core.AssetsManager;
import com.mygdx.game.screens.chapter1.StreetChapter1Screen;

public class MyGame extends Game {
    // http://stackoverflow.com/questions/27560783/libgdx-translating-a-scene2d-camera
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

	@Override
	public void create () {
        if (FULLSCREEN) Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        assetsManager = new AssetsManager();
        batch = new PolygonSpriteBatch();
        player = new Player(this, true, 0);

        // load assets and start screen
        assetsManager.loadChapter1();
        setScreen(new StreetChapter1Screen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        assetsManager.dispose();
        batch.dispose();
    }
}