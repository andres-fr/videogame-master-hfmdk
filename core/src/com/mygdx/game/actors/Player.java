package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.reflect.Method;
import com.mygdx.game.MyGame;
import com.mygdx.game.core.*;

import static com.badlogic.gdx.utils.TimeUtils.nanoTime;

/**
 * Created by afr on 26.08.16.
 */

public class Player extends com.mygdx.game.core.MyActor {
    Array<TextureAtlas.AtlasRegion> walkregs;
    float speed = 300; // in pixels per second
    boolean walking = false;
    long timeStamp;


    public Player(boolean touchable, int initCell, MyGame g) {
        super(touchable, initCell, g);
        walkregs = getRegions("walk.left");
        timeStamp = nanoTime();

        this.addListener(new GameActorGestureListener(0.4f) {
            @Override
            public boolean longPress(Actor actor, float x, float y) {
                setColor(MathUtils.random(0, 1f), MathUtils.random(0, 1f), MathUtils.random(0, 1f), 1);
                return super.longPress(actor, x, y);
            }


        });
    }

    private void standStill() {
        changeCell(0);
        walking = false;
    }

    private void startWalk() {
        changeCell(0);
        walking = true;
    }

    private void walkForwards() {
        changeCell((getCell()+1) % walkregs.size);
    }

    public void walkTo(float x, float y) {
        float time = (new Vector2(x-getWidth()*getScaleX()/2, y-getHeight()*getScaleY()/2).dst(getX(), getY()))/speed;
        clearActions();
        startWalk();
        addAction(Actions.sequence(Actions.moveTo(x-getWidth()*getScaleX()/2, y-getHeight()*getScaleY()/2, time),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        standStill();
                    }
                })));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (walking && nanoTime() - timeStamp > 0.1 * 1e9) {
            timeStamp = nanoTime();
            walkForwards();

        }
        //System.out.println(delta);
    }
}
