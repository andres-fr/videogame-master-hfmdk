package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.reflect.Method;
import com.mygdx.game.MyGame;
import com.mygdx.game.core.*;

/**
 * Created by afr on 26.08.16.
 */

public class Player extends com.mygdx.game.core.MyActor {
    Array<TextureAtlas.AtlasRegion> walkregs;


    public Player(boolean touchable, int initCell, MyGame g) {
        super(touchable, initCell, g);
        walkregs = getRegions("walk.left");

        this.addListener(new InputListener(){
            float downX, downY;
            Vector2 stageDown, stageDragged;
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                downX = x;
                downY = y;
                stageDown = localToStageCoordinates(new Vector2(x, y));
                nextCell();
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (stageDragged != null) {
                    getStage().getCamera().translate(stageDragged.x-stageDown.x, 0, 0);
                }
                nextCell();
            }
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                stageDragged = localToStageCoordinates(new Vector2(x, y));
                setPosition(stageDragged.x-downX, stageDragged.y-downY);
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


    private void nextCell() {
        changeCell((getCell()+1) % walkregs.size);

    }
}
