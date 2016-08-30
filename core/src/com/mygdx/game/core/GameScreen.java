package com.mygdx.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGame;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.badlogic.gdx.utils.TimeUtils.nanoTime;

/**
 * Created by afr on 23.08.16.
 */

public class GameScreen implements Screen {
    protected MyGame game;
    protected GameStage stage;
    protected float downScale, upScale;
    protected boolean travellingEnabled = false;
    protected Float travellingMargin = null; // 1 means the camera stands still until the Player hits the screen border
    protected float fitRatio;
    protected BackgroundTable background;
    protected Table shadows;
    protected Table lights;
    //protected Table foreground = new Table();
    protected Array<WalkZone> walkZones = new Array<WalkZone>();


    public GameScreen(MyGame g, String bgrnd, String shdw, String lght) {
        game = g;
        stage = new GameStage(g, this);
        cleanStage();
        setBackgroundSuite(bgrnd, shdw, lght);
        addBackgroundListener();
    }

    public void setActorScale(float dwn, float up) {
        downScale = dwn;
        upScale = up;
    }

    public void setTravellingMargin(float margin) {
        travellingEnabled = true;
        travellingMargin = margin;
    }

    public void disableTravelling() {
        travellingEnabled = false;
        travellingMargin = null;
    }

    public void cleanStage() {
        ((OrthographicCamera) stage.getCamera()).setToOrtho(false, MyGame.WIDTH, MyGame.HEIGHT);
        stage.setDebugAll(MyGame.DEBUG);
        // camera travelling disabled by default
        disableTravelling();
        // references for the dynamical scaling for 3d actors
        setActorScale(1, 1);
        // remove all actors and listeners from the stage
        stage.clear();
        // instantiate and add containers for BG
        background = new BackgroundTable(this);
        shadows = new Table();
        lights = new Table();
        stage.addActor(background);
        stage.addActor(shadows);
        stage.addActor(lights);
    }

    public void setBackgroundSuite(String bgrnd, String shdw, String lght) {
        fitRatio = ((float) MyGame.HEIGHT) / ((float) game.assetsManager.getRegion(bgrnd).getRegionHeight());
        background.setBackground(game.assetsManager.getRegionDrawable(bgrnd));
        background.setBounds(0, 0, game.assetsManager.getRegion(bgrnd).getRegionWidth() * fitRatio,
                game.assetsManager.getRegion(bgrnd).getRegionHeight() * fitRatio);
        shadows.setBackground(game.assetsManager.getRegionDrawable(shdw));
        shadows.setBounds(0, 0, game.assetsManager.getRegion(lght).getRegionWidth() * fitRatio,
                game.assetsManager.getRegion(lght).getRegionHeight() * fitRatio);
        lights.setBackground(game.assetsManager.getRegionDrawable(lght));
        lights.setBounds(0, 0, game.assetsManager.getRegion(shdw).getRegionWidth() * fitRatio,
                game.assetsManager.getRegion(shdw).getRegionHeight() * fitRatio);
    }

    public void addBackgroundListener() {
        background.setTouchable(Touchable.enabled);
        background.addListener(new InputListener() {
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


    @Override
    public void render(float delta) {
        if (travellingEnabled) cameraFollowsPlayer();
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


    private void cameraFollowsPlayer() {
        float playerX = game.player.getCenter().x;
        float camX = stage.getCamera().position.x;
        float margin = MyGame.WIDTH * travellingMargin / 2;
        if (playerX < camX-margin) {
            stage.getCamera().translate(playerX-(camX-margin), 0, 0);
        } else if (playerX > camX + margin) {
            stage.getCamera().translate(playerX-(camX+margin), 0, 0);
        }
        if (stage.getCamera().position.x < MyGame.WIDTH / 2){
            stage.getCamera().position.x = MyGame.WIDTH / 2;
        }
        if (stage.getCamera().position.x > background.getWidth() - MyGame.WIDTH / 2) {
            stage.getCamera().position.x = background.getWidth() - MyGame.WIDTH / 2;
        }
    }

    // override this in the scenes
    public void backgroundTouchedDown(float x, float y) {
    }

    public float getScaleFromStageY(float yPos) {
        float yRel = (yPos / background.getHeight()) * (upScale - downScale) + downScale;
        return yRel;
    }

    /**
     * rescales and adds the given walking zone to the screen
     *
     * @param points an array of integers designing the x, y, x, y... coordinates for the
     *               vertices of a walkZone (Polygon), as they appear on the background image
     *               without scaling and starting by (x,y)=(0,0) in the lower-left corner.
     */
    public void addWalkzoneScaled(int[] points) {
        float[] scaled = new float[points.length];
        for (int i = 0; i < points.length; i++) {
            scaled[i] = points[i]*fitRatio;
        }
        walkZones.add(new WalkZone(scaled));
    }

    public Array<WalkZone> getWalkZones() {
        return walkZones;
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
