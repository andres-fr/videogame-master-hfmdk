package com.mygdx.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
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
    protected float downScale, upScale;
    protected float rightCamMargin, leftCamMargin;
    protected Table background = new Table();
    protected Table shadows  = new Table();
    protected Table lights  = new Table();


    public GameScreen(MyGame g, String bgrnd, String shdw, String lght, float downScale, float upScale,
                      float rightCamMargin, float leftCamMargin) {
        game = g;
        stage = new GameStage(g, new FitViewport(MyGame.WIDTH, MyGame.HEIGHT));
        timeStamp = nanoTime();
        ((OrthographicCamera)stage.getCamera()).setToOrtho(false, MyGame.WIDTH, MyGame.HEIGHT);
        stage.setDebugAll(MyGame.DEBUG);
        // references for the dynamical scaling for 3d actors
        this.downScale = downScale;
        this.upScale = upScale;
        // references to limit the camera travelling
        this.rightCamMargin = rightCamMargin;
        this.leftCamMargin = leftCamMargin;


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
        // detect touchDowns on background:
        background.setTouchable(Touchable.enabled);
        background.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                backgroundTouchedDown(x, y);
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }


    // TODO revisar esta guarrada
    @Override
    public void render(float delta) {
        //System.out.println(stage.getCamera().position);
        stage.getCamera().position.x = game.player.getX();
        if (game.player.getX() < leftCamMargin) {
            //stage.getCamera().translate(game.player.getX()-leftCamMargin, 0, 0);

        } else if (game.player.getX() > rightCamMargin) {
            //stage.getCamera().translate(game.player.getX()-rightCamMargin, 0, 0);
        }

        if (stage.getCamera().position.x < MyGame.WIDTH/2) stage.getCamera().position.x = MyGame.WIDTH/2;
        if (stage.getCamera().position.x > background.getWidth()-MyGame.WIDTH/2) stage.getCamera().position.x = background.getWidth()-MyGame.WIDTH/2;

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
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


    // override this in the scenes
    public void backgroundTouchedDown(float x, float y) {
    }

    public float getScaleFromStageY(float yPos) {
        float yRel = (yPos/background.getHeight())*(upScale-downScale)+downScale;
        return yRel;
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
