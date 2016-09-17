package com.mygdx.game.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGame;

/**
 * Created by afr on 26.08.16.
 */

public class GameActor extends Actor {
    protected Array<TextureAtlas.AtlasRegion> cellArray;
    int cell;
    Rectangle bounds = new Rectangle();
    protected MyGame game;


    public GameActor(MyGame g, Array<TextureAtlas.AtlasRegion> cells, boolean touchable, int initCell) {
        super();
        game = g;
        cellArray = cells;
        setColor(Color.WHITE);
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
        return cellArray.get(idx);
        //return game.assetsManager.getCurrentRegion(idx);
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);
        batch.draw(getRegion(cell), getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    public boolean collidesWith(GameActor a) {
        return bounds.overlaps(a.getBounds());
    }

    public Vector2 getXY() {
        return new Vector2(getX(), getY());
    }

    public Vector2 getQuarterSize() {
        return new Vector2(getWidth() * getScaleX() / 2, getHeight() * getScaleY() / 2);
    }

    public Vector2 getCenter() {
        return getXY().add(getQuarterSize());
    }

    public Vector2 getFoot() {
        return getXY().add(getQuarterSize().x, 0);
    }

    public Vector2 destinyCentered(float x, float y) {
        return new Vector2(x, y).sub(getQuarterSize());
    }

    public Vector2 destinyStanding(float x, float y) {
        return new Vector2(x, y).sub(getQuarterSize().x, 0);
    }
}
