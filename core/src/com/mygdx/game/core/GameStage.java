package com.mygdx.game.core;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGame;

/**
 * Created by afr on 26.08.16.
 * same as Stage, but with compulsory game field (called by reference)
 */

public class GameStage extends Stage {
    public MyGame game;

    public GameStage(MyGame g) {
        super();
        game = g;
    }

    public GameStage(MyGame g, Viewport viewport) {
        super(viewport);
        game = g;
    }

    public GameStage(MyGame g, Viewport viewport, Batch batch) {
        super(viewport, batch);
        game = g;
    }
}
