package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.core.AssetsLoader;
import com.mygdx.game.screens.main_menu.MainMenuScreen;
import com.mygdx.game.screens.main_menu.PresentationScreen;

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
