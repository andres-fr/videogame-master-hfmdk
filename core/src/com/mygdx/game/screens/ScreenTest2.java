package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGame;
import com.mygdx.game.actors.MyActor;
import com.mygdx.game.actors.Policeman;
import com.mygdx.game.core.GameScreen;

import static com.badlogic.gdx.utils.TimeUtils.nanoTime;


/**
 * Created by afr on 23.08.16.
 */

public class ScreenTest2 extends GameScreen {

    //String[] files = {"big-bg.png", "airplane.png"};
    String[] files = {"background.png", "lights.png", "shadows.png"};
    // Array<TextureRegionDrawable> regions = new Array<TextureRegionDrawable>();
    Array<Table> backgrounds = new Array<Table>();
    int idx = 0;
    long initTime;

    private Array<MyActor> actors = new Array<MyActor>();


    public ScreenTest2(MyGame g) {
        super(g);
        initTime = nanoTime();


        // add backgrounds
        for (String str : files) {
            TextureRegionDrawable reg = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("backgrounds/" + str))));
            //regions.add(reg);
            Table t = new Table();
            t.setBackground(reg);
            t.setBounds(0, 0, MyGame.WIDTH, MyGame.HEIGHT);
            t.setClip(true);
            //t.setFillParent(true);
            backgrounds.add(t);
        }
        backgrounds.get(0).setBounds(0, 0, MyGame.WIDTH * 3.8f, MyGame.HEIGHT);
        backgrounds.get(1).setBounds(0, 0, MyGame.WIDTH * 3.8f, MyGame.HEIGHT);
        backgrounds.get(2).setBounds(0, 0, MyGame.WIDTH * 3.8f, MyGame.HEIGHT);
        stage.addActor(backgrounds.get(0));
        stage.addActor(backgrounds.get(1));
        stage.addActor(backgrounds.get(2));
        float cycleTime = 6f;
        backgrounds.get(0).addAction(Actions.forever(Actions.sequence(Actions.color(Color.WHITE, cycleTime), Actions.color(Color.NAVY, cycleTime))));
        backgrounds.get(1).addAction(Actions.forever(Actions.sequence(Actions.fadeOut(cycleTime), Actions.fadeIn(cycleTime))));
        backgrounds.get(2).addAction(Actions.forever(Actions.sequence(Actions.fadeOut(cycleTime), Actions.fadeIn(cycleTime))));
        // add and configure actor 1
        MyActor a = new MyActor(false);
        a.setName("actor1");
        //stage.addActor(a);
        a.addAction(Actions.forever(Actions.sequence(Actions.moveTo(0, 0), Actions.moveTo(100, 500, 3))));
        // add and configure actor 2
        MyActor b = new MyActor(true);
        b.setName("actor2");
        stage.addActor(b);
        actors.add(a);
        b.setColor(1, 1, 1, 1);
        b.setPosition(200, 200);
        actors.add(b);
        // add and configure policeman
        Policeman p = new Policeman(true);
        p.setName("policeman1");
        //stage.addActor(p);
        p.setColor(1, 1, 1, 1);
        p.setPosition(500, 0);
        //p.setScale(0.5f, 0.5f);
    }

    @Override
    public void render(float delta) {
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
