package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGame;
import com.mygdx.game.actors.MyActor;
import com.mygdx.game.core.GameScreen;

import static com.badlogic.gdx.utils.TimeUtils.nanoTime;


/**
 * Created by afr on 23.08.16.
 */

public class ScreenTest1 extends GameScreen {

    String[] files = {"village.png", "sea.jpg", "city-windows.png", "airplane.png"};
    Array<Drawable> tests = new Array<Drawable>();
    Array<Table> backgrounds = new Array<Table>();
    Table bg;
    int idx = 0;
    long initTime;

    private Array<MyActor> actors = new Array<MyActor>();


    public ScreenTest1(MyGame g) {
        super(g);
        initTime = nanoTime();


        // add backgrounds
        for (String str : files) {
            TextureRegionDrawable reg = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("backgrounds/" + str))));
            tests.add(reg);
            Table t = new Table();
            t.setBackground(reg);
            t.setBounds(0, 0, MyGame.WIDTH, MyGame.HEIGHT);
            t.setClip(true);
            //t.setFillParent(true);
            backgrounds.add(t);
        }
        bg = backgrounds.get(idx);
        stage.addActor(bg); // add gaia bg
        stage.addActor(backgrounds.get(3)); // add aplha plane

        // add and configure actor 1
        MyActor a = new MyActor(false);
        a.setName("actor1");
        stage.addActor(a);
        a.addAction(Actions.forever(Actions.sequence(Actions.moveTo(0, 0), Actions.moveTo(100, 500, 3))));
        // add and configure actor 2
        MyActor b = new MyActor(true);
        b.setName("actor2");
        stage.addActor(b);
        actors.add(a);
        b.setColor(1, 1, 1, 1);
        b.setPosition(200, 200);
        actors.add(b);
    }

    @Override
    public void render(float delta) {
        /*
        if ((nanoTime() - initTime) >= 3e9) {
            initTime = nanoTime();
            System.out.println(nanoTime());
            idx = (idx + 1) % files.length;
            bg = backgrounds.get(idx);
        }
        */
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        if (actors.get(0).collidesWith(actors.get(1))) {
            actors.get(0).setColor(1, 0, 0, 1);
        } else actors.get(0).setColor(1, 1, 1, 1);
    }
}
