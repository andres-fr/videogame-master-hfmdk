package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.actors.Player;
import com.mygdx.game.core.AssetsManager;
import com.mygdx.game.screens.ScreenStreet1;

public class MyGame extends Game {
    // http://stackoverflow.com/questions/27560783/libgdx-translating-a-scene2d-camera
    public final static int WIDTH = 1280;
    public final static int HEIGHT = 720;
    public final static boolean DEBUG = true;
    public final static boolean FULLSCREEN = !DEBUG;
    public final static String VERSION = "0.0";
    // game related instances
    public AssetsManager assetsManager = new AssetsManager();
    public Player player;


	@Override
	public void create () {
        if (FULLSCREEN) Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

        assetsManager.prepareScreenTest3();
        player = new Player(true, 0, this);
        setScreen(new ScreenStreet1(this));
	}
}
