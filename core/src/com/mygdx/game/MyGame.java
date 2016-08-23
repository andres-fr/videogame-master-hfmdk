package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.ScreenTest1;

public class MyGame extends Game {
    public final static int WIDTH = 1280;
    public final static int HEIGHT = 720;
    public final static boolean DEBUG = false;
    public final static boolean FULLSCREEN = !DEBUG;
    public final static String VERSION = "0.0";


	@Override
	public void create () {
        if (FULLSCREEN) Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        setScreen(new ScreenTest1(this));
	}
}
