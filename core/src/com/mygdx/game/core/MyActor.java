package com.mygdx.game.core;

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
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.reflect.Method;
import com.mygdx.game.MyGame;
import com.mygdx.game.actors.BadActor;

/**
 * Created by afr on 26.08.16.
 */

public class MyActor extends Actor {
    Color color = Color.WHITE;
    int cell;
    Rectangle bounds = new Rectangle();
    public MyGame game;


    public MyActor (boolean touchable, int initCell, MyGame g) {
        super();
        game = g;

        if (!touchable) setTouchable(Touchable.disabled);
        changeCell(initCell);
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

    public int getCell() {
        return cell;
    }

    public void changeCell(int newCell) {
        cell = newCell;
        setSize(getRegion(cell).getRegionWidth(), getRegion(cell).getRegionHeight());
    }


    /**
     * gets the region with idx from the atlas currently active in the AssetsManager
     * @param idx
     * @return
     */
    public TextureAtlas.AtlasRegion getRegion(int idx) {
        return game.assetsManager.getRegion(idx);
    }

    public Array<TextureAtlas.AtlasRegion> getRegions(String name) {
        return game.assetsManager.getCurrentAtlas().findRegions(name);
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(getRegion(cell), getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    public boolean collidesWith(BadActor a) {
        return bounds.overlaps(a.getBounds());
    }
}
