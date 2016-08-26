package com.mygdx.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGame;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.timeScale;
import static com.badlogic.gdx.utils.TimeUtils.nanoTime;

/**
 * Created by afr on 23.08.16.
 */

public class GameScreen implements Screen {
    protected MyGame game;
    protected GameStage stage;
    protected long timeStamp;
    protected Table background = new Table();
    protected Table shadows  = new Table();
    protected Table lights  = new Table();

    public GameScreen(MyGame g, String bgrnd, String shdw, String lght) {
        game = g;
        stage = new GameStage(g, new FitViewport(MyGame.WIDTH, MyGame.HEIGHT));
        timeStamp = nanoTime();
        ((OrthographicCamera)stage.getCamera()).setToOrtho(false, MyGame.WIDTH, MyGame.HEIGHT);
        stage.setDebugAll(MyGame.DEBUG);


        // configure and add the bg layers
        float fitRatio = ((float)g.HEIGHT)/((float)g.assetsManager.getRegion(bgrnd).getRegionHeight());
        stage.addActor(background);
        background.setBackground(g.assetsManager.getRegionDrawable(bgrnd));
        background.setBounds(0, 0, g.assetsManager.getRegion(bgrnd).getRegionWidth()*fitRatio, g.assetsManager.getRegion(bgrnd).getRegionHeight()*fitRatio);
        stage.addActor(shadows);
        shadows.setBackground(g.assetsManager.getRegionDrawable(shdw));
        shadows.setBounds(0, 0, g.assetsManager.getRegion(lght).getRegionWidth()*fitRatio, g.assetsManager.getRegion(lght).getRegionHeight()*fitRatio);
        stage.addActor(lights);
        lights.setBackground(g.assetsManager.getRegionDrawable(lght));
        lights.setBounds(0, 0, g.assetsManager.getRegion(shdw).getRegionWidth()*fitRatio, g.assetsManager.getRegion(shdw).getRegionHeight()*fitRatio);
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
     * PROBLEM WITH ASSETMANAGER WHEN SWITCHING SCREENS?? REVISE
     * @param nxt the screen to go to
     * @param fadeOutTime in seconds
     * @param fadeInTime in seconds
     * @return the Action of changing screen. To actually do it, call addAction(gotoScreen(...))
     */
    /*
    public Action gotoScreen(final GameScreen nxt, float fadeOutTime, final float fadeInTime) {
        Runnable r = new Runnable() {
            public void run() {
                nxt.stage.addAction(sequence(fadeOut(0), fadeIn(fadeInTime)));
                game.setScreen(nxt);
            }
        };
        return sequence(fadeOut(fadeOutTime), run(r), fadeIn(fadeInTime));
    }
    */


}
