package com.mygdx.game.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by afr on 11.08.16.
 */

public class TintedImage extends Image {

    public TintedImage(Texture texture) {
        super(texture);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(1, 0.5f, 0.5f, 0.5f);
        super.draw(batch, parentAlpha);
    }
}
