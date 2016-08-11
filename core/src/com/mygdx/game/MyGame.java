package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.mygdx.game.core.AssetsLoader;
import com.mygdx.game.core.GameScreen;
import com.mygdx.game.core.MenuScreen;
import com.mygdx.game.screens.PresentationScreen;
import com.mygdx.game.screens.menus.MainMenuScreen;
import com.mygdx.game.screens.menus.PauseMenuScreen;

import static com.badlogic.gdx.utils.TimeUtils.nanoTime;

public class MyGame extends Game {
	public final static int WIDTH = 1280;
	public final static int HEIGHT = 720;
	public final static boolean DEBUG = false;
    public final static boolean FULLSCREEN = !DEBUG;
	public final static String VERSION = "0.0";
    public PauseMenuScreen pauseMenu;// set by the main menu
    private long lastNanoTime;

	@Override
	public void create () {
		if (FULLSCREEN) Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		AssetsLoader.load();
        PresentationScreen presentationScreen = new PresentationScreen(this);
        pauseMenu = new PauseMenuScreen(presentationScreen);
        lastNanoTime = nanoTime();

        setScreen(presentationScreen);
		//setScreen(new MainMenuScreen(presentationScreen));
	}

    public void setLastNanoTime(long lastNanoTime) {
        this.lastNanoTime = lastNanoTime;
        if (DEBUG) System.out.println("last action nanotime: "+lastNanoTime);
    }

    public double secondsWithoutAction(long nanoTime) {
        return (nanoTime-lastNanoTime)/1e9;
    }

    @Override
    public void setScreen(Screen screen) {
        setLastNanoTime(nanoTime());
        super.setScreen(screen);
    }
}
