package com.mygdx.game.core;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGame;
import javax.validation.constraints.NotNull;


/**
 * Created by afr on 26.08.16.
 */

public class GameActor extends Actor {
    protected Array<TextureAtlas.AtlasRegion> cellArray = null;
    int cell = -1;
    protected MyGame game;
    protected Rectangle bounds = new Rectangle();

    public GameActor(MyGame g, int posX, int posY, int width, int height, boolean touchable) {
        super();
        game = g;
        this.setPosition(posX, posY);
        this.setSize(width, height);
        if (!touchable) setTouchable(Touchable.disabled);
    }


    public GameActor(MyGame g, int posX, int posY, boolean touchable,
                     @NotNull Array<TextureAtlas.AtlasRegion> cellRegions, int initCell) {
        this(g, posX, posY, 0, 0, touchable);  // !!!! this 0, 0 works under the assumption that changeCell will change them rightafter
        cellArray = cellRegions;
        changeCell(initCell);
    }


    public int getCell() {
        return cell;
    }

    protected void changeCell(int newCell) {
        cell = newCell;
        if (cellArray!=null){
            setSize(cellArray.get(cell).getRegionWidth(), cellArray.get(cell).getRegionHeight());
        }
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
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);
        if (cellArray != null) {
            batch.draw(cellArray.get(cell), getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
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

    public boolean collidesWith(GameActor a) {
        return bounds.overlaps(a.getBounds());
    }
}
