package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGame;
import com.mygdx.game.core.AssetsLoader;

import static com.badlogic.gdx.utils.TimeUtils.nanoTime;

/**
 * Created by afr on 11.08.16.
 */



public class Policeman extends Actor {

    Array<AtlasRegion> textures = AssetsLoader.policeman.getRegions();
    int idx  = 0;
    int MAX_IDX = textures.size;
    long snapshot;

    public Policeman() {
        super();
        snapshot = nanoTime();
        resize();


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(textures.get(idx), getX(), getY(), getWidth()*getScaleX(), getHeight()*getScaleY());
        if (nanoTime() - snapshot > 0.1*1e9) {
            snapshot = nanoTime();
            idx = (idx+1)%MAX_IDX;
            resize();
            debugPrint();
               }
    }


    private void resize() {
        setSize(textures.get(idx).getRegionWidth()*getScaleX(), textures.get(idx).getRegionHeight()*getScaleY());
        //setX(0);
        setPosition(getX(), getY());
        //setBounds(getX(), getY(), textures.get(idx).getRegionWidth()*getScaleX(), textures.get(idx).getRegionHeight()*getScaleY());
    }


    public void debugPrint() {
        System.out.println("x: "+getX()+"   y: "+getY()+"   w: "+getWidth()+"   h: "+getHeight()+"   scale: "+getScaleX()+","+getScaleY());

    }
}
