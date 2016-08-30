package com.mygdx.game.core;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGame;

/**
 * Created by afr on 26.08.16.
 * same as Stage, but with compulsory game field (called by reference)
 */

public class GameStage extends Stage {
    public MyGame game;
    public GameScreen gameScreen;

    public GameStage(MyGame g, GameScreen gs) {
        super(new FitViewport(MyGame.WIDTH, MyGame.HEIGHT), new PolygonSpriteBatch());
        game = g;
        gameScreen = gs;
    }
}
