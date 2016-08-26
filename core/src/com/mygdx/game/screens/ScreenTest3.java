package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGame;
import com.mygdx.game.actors.BadActor;
import com.mygdx.game.actors.Policeman;
import com.mygdx.game.core.GameScreen;


/**
 * Created by afr on 23.08.16.
 */

public class ScreenTest3 extends GameScreen {

    public ScreenTest3(MyGame g) {
        super(g, "street_bg", "street_shadows", "street_lights", 0.6f, 0.2f, 100, 900);



        float cycleTime = 6f;
        background.addAction(Actions.forever(Actions.sequence(Actions.color(Color.WHITE, cycleTime), Actions.color(Color.NAVY, cycleTime))));
        lights.addAction(Actions.forever(Actions.sequence(Actions.fadeOut(cycleTime), Actions.fadeIn(cycleTime))));
        shadows.addAction(Actions.forever(Actions.sequence(Actions.fadeOut(cycleTime), Actions.fadeIn(cycleTime))));

        // add and configure Player initial pos
        stage.addActor(game.player);
        g.player.setPosition((g.WIDTH-g.player.getWidth())/2, 100);
        g.player.setScale(0.5f);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //resize player (maybe not the best place??)
        game.player.setScale(getScaleFromStageY(game.player.getY()));
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        // player management
        /*
        if (actors.get(0).collidesWith(actors.get(1))) {
            actors.get(0).setColor(1, 0, 0, 1);
        } else actors.get(0).setColor(1, 1, 1, 1);
        */
    }


    @Override
    public void backgroundTouchedDown(float x, float y) {
        super.backgroundTouchedDown(x, y);
        game.player.walkTo(x, y);
    }
}
