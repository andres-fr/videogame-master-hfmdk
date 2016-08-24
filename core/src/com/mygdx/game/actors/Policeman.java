package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Array;

/**
 * Created by afr on 23.08.16.
 */

public class Policeman extends Actor {
    TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("atlases/policeman.atlas"));
    Array<TextureAtlas.AtlasRegion> regions = atlas.getRegions();
    Color color;
    int idx = 0;
    Rectangle bounds = new Rectangle();


    public Policeman (boolean touchable) {
        setSize(regions.get(idx).getRegionWidth(), regions.get(idx).getRegionHeight());
        if (!touchable) setTouchable(Touchable.disabled);


        this.addListener(new InputListener(){
            float downX, downY;
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                downX = x;
                downY = y;
                idx = (idx+1)%regions.size;
                setSize(regions.get(idx).getRegionWidth(), regions.get(idx).getRegionHeight());
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


    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        super.setBounds(x, y, width, height);
        this.bounds.set(x, y, width, height);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setBounds(getX(), getY(), getWidth(), getHeight());

    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(regions.get(idx), getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    public boolean collidesWith(MyActor a) {
        return bounds.overlaps(a.getBounds());
    }
}
