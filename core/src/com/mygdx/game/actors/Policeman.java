package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGame;
import com.mygdx.game.core.*;

/**
 * Created by afr on 23.08.16.
 */

public class Policeman extends com.mygdx.game.core.MyActor {
    TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("atlases/policeman.atlas"));
    Array<TextureAtlas.AtlasRegion> regions = atlas.getRegions();


    public Policeman (boolean touchable, int initCell, MyGame g) {
        super(touchable, initCell, g);
        this.addListener(new InputListener(){
            float downX, downY;
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                downX = x;
                downY = y;
                changeCell((getCell() + 1) % regions.size);
                return true;
            }
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                Vector2 v = localToStageCoordinates(new Vector2(x, y));
                setPosition(v.x-downX, v.y-downY);
            }
        });

        this.addListener(new ActorGestureListener() {
            @Override
            public boolean longPress(Actor actor, float x, float y) {
                if (getColor().r == 0) {
                    setColor(1, 1, 1, 1);
                } else setColor(0, 1, 0, 1);
                return super.longPress(actor, x, y);
            }
        });
    }
}
