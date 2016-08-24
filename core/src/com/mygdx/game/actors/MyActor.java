package com.mygdx.game.actors;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

/**
 * Created by afr on 23.08.16.
 */

public class MyActor extends Actor {
    Texture img;
    TextureRegion region;
    Color color;
    Rectangle bounds = new Rectangle();

    public MyActor (boolean touchable) {
        img = new Texture("badlogic.jpg");
        region = new TextureRegion(img);
        setSize(region.getRegionWidth(), region.getRegionHeight());
        if (!touchable) setTouchable(Touchable.disabled);


        this.addListener(new InputListener(){
            float downX, downY;
            Vector2 stageDown, stageDragged;
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                downX = x;
                downY = y;
                stageDown = localToStageCoordinates(new Vector2(x, y));
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Vector2 globalUp = localToStageCoordinates(new Vector2(x, y));
                getStage().getCamera().translate(stageDragged.x-stageDown.x, 0, 0);
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

            @Override
            public void zoom(InputEvent event, float initialDistance, float distance) {
                System.out.println(initialDistance+", "+distance);
                super.zoom(event, initialDistance, distance);
            }
        });
    }

    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        super.setBounds(x, y, width, height);
        this.bounds.set(x, y, width, height);
    }

    public boolean collidesWith(MyActor a) {
        return bounds.overlaps(a.getBounds());
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setBounds(getX(), getY(), getWidth(), getHeight());
    }
}
