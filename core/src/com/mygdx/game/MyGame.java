package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.core.AssetsLoader;
import com.mygdx.game.screens.PresentationScreen;

public class MyGame extends Game {
	public final static int WIDTH = 1280;
	public final static int HEIGHT = 720;
	public final static boolean DEBUG = false;
	public final static String VERSION = "0.0";

	@Override
	public void create () {
		AssetsLoader.load();
		setScreen(new PresentationScreen(this));
		//setScreen(new MainMenuScreen(this));
	}
}
