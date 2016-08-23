package com.mygdx.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGame;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by afr on 23.08.16.
 */

public class GameScreen implements Screen {
    protected MyGame game;
    protected Stage stage = new Stage(new FitViewport(MyGame.WIDTH, MyGame.HEIGHT));

    public GameScreen(MyGame g) {
        game = g;
        ((OrthographicCamera)stage.getCamera()).setToOrtho(false, MyGame.WIDTH, MyGame.HEIGHT);
        stage.setDebugAll(MyGame.DEBUG);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    /**
     * @param nxt the screen to go to
     * @param fadeOutTime in seconds
     * @param fadeInTime in seconds
     * @return the Action of changing screen. To actually do it, call addAction(gotoScreen(...))
     */
    public Action gotoScreen(final GameScreen nxt, float fadeOutTime, final float fadeInTime) {
        Runnable r = new Runnable() {
            public void run() {
                nxt.stage.addAction(sequence(fadeOut(0), fadeIn(fadeInTime)));
                game.setScreen(nxt);
            }
        };
        return sequence(fadeOut(fadeOutTime), run(r), fadeIn(fadeInTime));
    }


}
