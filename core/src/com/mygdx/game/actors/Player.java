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
    Array<TextureAtlas.AtlasRegion> walkCells;
    Array<WalkZone> walkZones = null; // since there is only 1 Player obj for many screens. see getCurrentWalkzone
    float speed = 300; // in pixels per second
    boolean walking = false;
    long timeStamp;


    public Player(boolean touchable, int initCell, MyGame g) {
        super(touchable, initCell, g);
        walkCells = getRegions("walk.left");
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
        walking = false;
        changeCell(0);
    }

    private void startWalk() {
        walking = true;
    }

    private void walkForwards() {
        changeCell((getCell()+1) % walkCells.size);
    }

    public void walkTo(float x, float y) {
        Vector2 destiny = destinyStanding (x, y);
        float time = destiny.dst(getXY())/speed;
        clearActions();
        startWalk();
        addAction(Actions.sequence(Actions.moveTo(destiny.x, destiny.y, time), Actions.run(new Runnable() {
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
            Vector2 footPos = getFoot();
            for (WalkZone wz : getCurrentWalkzones()) {
                if (!wz.contains(footPos.x, footPos.y)) {
                    clearActions();
                    standStill();
                    break;
                }
            }
        }
    }

    private Array<WalkZone> getCurrentWalkzones() {
        return ((GameStage)getStage()).gameScreen.getWalkZones();
    }
}
