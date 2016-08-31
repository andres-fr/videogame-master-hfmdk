package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.actors.Player;
import com.mygdx.game.core.AssetsManager;
import com.mygdx.game.core.GameScreen;

public class MyGame extends Game {
    // http://stackoverflow.com/questions/27560783/libgdx-translating-a-scene2d-camera
    public final static int WIDTH = 1280;
    public final static int HEIGHT = 720;
    public final static boolean DEBUG = false;
    public final static boolean FULLSCREEN = !DEBUG;
    public final static String VERSION = "0.0";
    // game related instances
    public AssetsManager assetsManager = new AssetsManager();
    public Stage stage;
    public GameScreen mainScreen;
    public Player player;

	@Override
	public void create () {
        if (FULLSCREEN) Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

        assetsManager.prepareScene1();
        stage = new Stage(new FitViewport(MyGame.WIDTH, MyGame.HEIGHT), new PolygonSpriteBatch());
        player = new Player(this, true, 0);
        mainScreen = new GameScreen(this);

        // configure screen for test
        mainScreen.cleanScreen();
        mainScreen.setBackgroundSuite("testImg", "testImg", "testImg");
        mainScreen.addBackgroundListener();
        setScreen(mainScreen);
	}

    @Override
    public void dispose() {
        super.dispose();
        assetsManager.dispose();
        mainScreen.dispose();
        stage.dispose();
    }
}
