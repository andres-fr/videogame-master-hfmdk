package com.mygdx.game.core;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.MyGame;

/**
 * Created by afr on 29.08.16.
 */

public class BackgroundTable extends Table {
    GameScreenRaw gameScreen;

    public BackgroundTable(GameScreenRaw gs) {
        super();
        gameScreen = gs;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (MyGame.DEBUG){
            for (WalkZone wz : gameScreen.walkZones) {
                ((PolygonSpriteBatch)batch).draw(wz.getPolygonRegion(), 0, 0);
            }
        }
    }


}
