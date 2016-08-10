package com.mygdx.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGame;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by afr on 08.08.16.
 */

public class GameScreen extends Table implements Screen {
    protected MyGame game;
    protected Color background;
    protected Stage stage = new Stage(new FitViewport(MyGame.WIDTH, MyGame.HEIGHT));

    public GameScreen(MyGame g) {
        game = g;
        background = new Color(0, 0, 0, 0);
        Gdx.gl.glClearColor(background.r, background.g, background.b, background.a);
        stage.addActor(this);
        setBounds(0, 0, MyGame.WIDTH, MyGame.HEIGHT);
        setDebug(MyGame.DEBUG);
        setClip(true);
        setFillParent(true);
    }

    public GameScreen(MyGame g, Color c) {
        game = g;
        background = c;
        Gdx.gl.glClearColor(background.r, background.g, background.b, background.a);
        stage.addActor(this);
        setBounds(0, 0, MyGame.WIDTH, MyGame.HEIGHT);
        setDebug(MyGame.DEBUG);
        setClip(true);
        setFillParent(true);
    }

    public MyGame getGame() {
        return game;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(background.r, background.g, background.b, background.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta); // Gdx.graphics.getDeltaTime()
        stage.draw();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // here the scene logic, also batch begin <DRAW> end if wanted
    }

    public Action alphaHill(float initTime, float fadeInTime, float inTime, float fadeOutTime, float endTime) {
        return sequence(fadeOut(0), fadeOut(initTime), fadeIn(fadeInTime), fadeIn(inTime),
                fadeOut(fadeOutTime), fadeOut(endTime));
    }

    public Action gotoScreen(final GameScreen nxt, float fadeOutTime, final float fadeInTime) {

        Runnable r = new Runnable() {
            public void run() {
                nxt.clearActions();
                nxt.addAction(sequence(fadeOut(0), fadeIn(fadeInTime)));
                game.setScreen(nxt);
            }
        };
        return sequence(fadeOut(fadeOutTime), run(r), fadeIn(fadeInTime));
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
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
