package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGame;
import com.mygdx.game.core.*;

import static com.badlogic.gdx.utils.TimeUtils.nanoTime;

/**
 * Created by afr on 26.08.16.
 */




public class Player extends GameActor {
    public enum STATES {IDLE, LEFT, RIGHT}
    private STATES currentState;
    long timeStamp;

    public Player(MyGame g) {
        super(g, 0, 0, true, g.assetsManager.getPermanentAtlas().findRegions("walk.left"), 0);
        SPEED = 200;
        timeStamp = nanoTime();
        setState(STATES.IDLE);

        this.addListener(new GameActorGestureListener(0.32f) {
            Vector2 stageTouchDown;
            boolean moveAfter = true;

            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                stageTouchDown = localToStageCoordinates(new Vector2(x, y));
                moveAfter = true;
            }

            @Override
            public boolean longPress(Actor actor, float x, float y) {
                getStage().addAction(game.actions.pauseGame());
                moveAfter = false;
                return super.longPress(actor, x, y);
            }

            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                if (moveAfter) {
                    walkToWalkZone(stageTouchDown.x, stageTouchDown.y);
                    moveAfter = true;
                }
            }
        });
    }

    public void setState(STATES state) {
        currentState = state;
        switch (state) {
            case IDLE:
                changeCell(0);
                addAction(Actions.moveTo(getX(), getY())); //!!!!!!!!!!!!!!!!!
                break;
            case LEFT:
                break;
            case RIGHT:
                break;
            default:
                break;
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (currentState!=STATES.IDLE && nanoTime() - timeStamp > 0.1 * 1e9) {
            timeStamp = nanoTime();
            changeCell((getCell()+1) % cellArray.size);
            Vector2 footPos = getFoot();
            boolean anyWalkZoneContainsPlayer = false;
            for (WalkZone wz : game.getCurrentScreen().getWalkZones()) {
                if (wz.contains(footPos.x, footPos.y)) {
                    anyWalkZoneContainsPlayer = true;
                    break;
                }
            }if (!anyWalkZoneContainsPlayer){
                //clearActions(); //!!!!!!! stops the player when outside a wz
                //setState(STATES.IDLE);
            }
        }
    }

    public Action actionWalkToAnyPoint(float x, float y) {
        clearActions(); /// !!!!!!! clears all actions inmediately before starting to walk
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                setState(Player.STATES.LEFT);
            }
        };
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                setState(Player.STATES.IDLE);
            }
        };
        return game.actions.moveActorToAnyPoint(this, x, y, r1, r2);
    }

    public void walkToAnyPoint(float x, float y) {
        addAction(actionWalkToAnyPoint(x, y));
    }

    public void walkToWalkZone(float x, float y) {
        for (WalkZone wz : game.getCurrentScreen().getWalkZones()) {
            if (wz.contains(x, y)){
                walkToAnyPoint(x, y);
                break;
            }
        }
    }
}