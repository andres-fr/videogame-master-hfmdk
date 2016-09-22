package com.mygdx.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGame;

import static com.badlogic.gdx.utils.TimeUtils.nanoTime;

/**
 * Created by afr on 15.09.16.
 * This class comprises the functionalities needed for any screen in this game:
 * Its stage uses the main PolygonBatch drawer present in MyGame, everything else is newly instantiated
 * and configured, including:
 * background ++ shadows/lights ++ foreground
 * with 2 nuances:
 * the WALKZONES and the FOREGROUND Table have to be added after instantiation, using the methods:
 * addWalkZoneScaled() and setForeground()
 */

public class GameScreenUI implements Screen {
    public MyGame game;
    public GameStage stage;
    public AssetsManager.PREPARE neededAsset;
    protected float screenFitRatio = 1;
    public BackgroundTable background;
    public Table shadows;
    public Table lights;
    public Table foreground;
    protected Array<WalkZone> walkZones = new Array<WalkZone>();


    long timeStamp; //!!!!!!


    /**
     *
     * @param g the MyGame instance (the app)
     * @param prepareAsset a value of the enum signaling the asset context which this screen is created in.
     */
    public GameScreenUI(MyGame g, AssetsManager.PREPARE prepareAsset) {
        // assign given references to local fields
        game = g;
        neededAsset = prepareAsset;
        game.assetsManager.prepare(prepareAsset);
        // create new stage always usign the same game batch, and configure
        stage = new GameStage(game);
        stage.addActor(game.omnipresentInvisibleActor);
        timeStamp = nanoTime();
    }

    public GameScreenUI(MyGame g, AssetsManager.PREPARE prepareAsset, String bgrnd) {
        this(g, prepareAsset);
        // add the background for the given bgrnd name
        background = new BackgroundTable(this);
        stage.addActor(background);
        screenFitRatio = ((float) MyGame.HEIGHT) / ((float) game.assetsManager.getCurrentRegion(bgrnd).getRegionHeight());
        background.setBackground(new TextureRegionDrawable(game.assetsManager.getCurrentRegion(bgrnd)));
        background.setBounds(0, 0, game.assetsManager.getCurrentRegion(bgrnd).getRegionWidth() * screenFitRatio,
                game.assetsManager.getCurrentRegion(bgrnd).getRegionHeight() * screenFitRatio);
    }

    public GameScreenUI(MyGame g, AssetsManager.PREPARE prepareAsset, String bgrnd, String shdw, String lghts) {
        this(g, prepareAsset, bgrnd);
        // add the shadows for the given shdw name
        shadows = new Table();
        stage.addActor(shadows);
        shadows.setBackground(new TextureRegionDrawable(game.assetsManager.getCurrentRegion(shdw)));
        shadows.setBounds(0, 0, game.assetsManager.getCurrentRegion(shdw).getRegionWidth() * screenFitRatio,
                game.assetsManager.getCurrentRegion(shdw).getRegionHeight() * screenFitRatio);
        // add the lights for the given lghts name
        lights = new Table();
        stage.addActor(lights);
        lights.setBackground(new TextureRegionDrawable(game.assetsManager.getCurrentRegion(lghts)));
        lights.setBounds(0, 0, game.assetsManager.getCurrentRegion(lghts).getRegionWidth() * screenFitRatio,
                game.assetsManager.getCurrentRegion(lghts).getRegionHeight() * screenFitRatio);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        if  (MyGame.DEBUG && (nanoTime()-timeStamp)>1e9){
            timeStamp = nanoTime();
            System.out.println("\n\nActors in current Screen: " + stage.getActors().toString());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) game.exit();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
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


    /**
     * the foreground cannot be added at construction, since the stage would put it in the background.
     * It needs to be added after every other actor
     * @param fgrnd the name of the foreground region in the assets
     */
    public void setForeground(String fgrnd) {
        foreground = new Table();
        stage.addActor(foreground);
        foreground.setBackground(new TextureRegionDrawable(game.assetsManager.getCurrentRegion(fgrnd)));
        foreground.setBounds(0, 0, game.assetsManager.getCurrentRegion(fgrnd).getRegionWidth() * screenFitRatio,
                game.assetsManager.getCurrentRegion(fgrnd).getRegionHeight() * screenFitRatio);
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
            scaled[i] = points[i]*screenFitRatio;
        }
        walkZones.add(new WalkZone(scaled));
    }

    public Array<WalkZone> getWalkZones() {
        return walkZones;
    }
}
