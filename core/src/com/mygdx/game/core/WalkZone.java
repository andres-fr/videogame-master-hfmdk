package com.mygdx.game.core;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Polygon;
import com.mygdx.game.MyGame;

/**
 * Created by afr on 30.08.16.
 */

public class WalkZone extends Polygon {
    private PolygonRegion polygonRegion = null;
    public WalkZone(float[] vertices) {
        super(vertices);
        if (MyGame.DEBUG) {
            Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            pix.setColor(0x00FF00AA);
            pix.fill();
            polygonRegion = new PolygonRegion(new TextureRegion(new Texture(pix)),
                    vertices, new EarClippingTriangulator().computeTriangles(vertices).toArray());
        }
    }

    public PolygonRegion getPolygonRegion() {
        return polygonRegion;
    }
}
