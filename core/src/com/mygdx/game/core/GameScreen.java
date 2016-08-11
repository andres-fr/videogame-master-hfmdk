package com.mygdx.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGame;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.addAction;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.badlogic.gdx.utils.TimeUtils.nanoTime;

/**
 * Created by afr on 08.08.16.
 */

public class GameScreen extends Table implements Screen {
    protected MyGame game;
    protected Color GLbackground;
    protected Stage stage = new Stage(new FitViewport(MyGame.WIDTH, MyGame.HEIGHT));

    public GameScreen(MyGame g) {
        GLbackground = new Color(0, 0, 0, 0);
        initConfiguration(g);
    }

    public GameScreen(MyGame g, Color c) {
        GLbackground = c;
        initConfiguration(g);
    }

    private void initConfiguration(MyGame g) {
        game = g;
        Gdx.gl.glClearColor(GLbackground.r, GLbackground.g, GLbackground.b, GLbackground.a);
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
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && game.secondsWithoutAction(nanoTime())>0.5) {
           gotoPauseScreen();
        }
        Gdx.gl.glClearColor(GLbackground.r, GLbackground.g, GLbackground.b, GLbackground.a);
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

    /**
     * @param nxt the screen to go to
     * @param fadeOutTime in seconds
     * @param fadeInTime in seconds
     * @return the Action of changing screen. To actually do it, call addAction(gotoScreen(...))
     */
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

    public void gotoPauseScreen() {
        game.pauseMenu.setBackScreen(this);
        addAction(gotoScreen(game.pauseMenu, 0, 0.5f));
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
    public void hide() {    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void addAction(Action a) {
        game.setLastNanoTime(nanoTime());
        super.addAction(a);
    }
}
