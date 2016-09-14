package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGame;
import com.mygdx.game.core.*;

import static com.badlogic.gdx.utils.TimeUtils.nanoTime;

/**
 * Created by afr on 26.08.16.
 */




public class Player extends GameActor {
    Array<TextureAtlas.AtlasRegion> walkCells;
    boolean walking = false;
    long timeStamp;

    public Player(MyGame g, boolean touchable, int initCell) {
        super(g, touchable, initCell);
        walkCells = getRegions("walk.left");
        timeStamp = nanoTime();

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
                setColor(MathUtils.random(0, 1f), MathUtils.random(0, 1f), MathUtils.random(0, 1f), 1);
                moveAfter = false;
                return super.longPress(actor, x, y);
            }

            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                if (moveAfter) {
                    walkTo(stageTouchDown.x, stageTouchDown.y);
                    moveAfter = true;
                }
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
        for (WalkZone wz : getCurrentWalkzones()) {
            if (wz.contains(x, y)){
                Vector2 destiny = destinyStanding (x, y);
                float time = destiny.dst(getXY())/game.PLAYER_SPEED;
                clearActions();
                startWalk();
                addAction(Actions.sequence(Actions.moveTo(destiny.x, destiny.y, time, Interpolation.pow2Out), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        standStill();
                    }
                })));
                break;
            }
        }
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

    @Override
    public void clearActions() {
        super.clearActions();
        standStill();
    }

    private Array<WalkZone> getCurrentWalkzones() {
        return game.mainScreen.getWalkZones();
    }
}
