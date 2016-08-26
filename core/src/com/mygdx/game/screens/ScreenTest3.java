package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
        super(g);
        g.assetsManager.prepareScreenTest2();

        // add and configure backgrounds
        background.setBackground(g.assetsManager.getRegionDrawable("street_bg"));
        lights.setBackground(g.assetsManager.getRegionDrawable("street_lights"));
        shadows.setBackground(g.assetsManager.getRegionDrawable("street_shadows"));
        background.setBounds(0, 0, g.assetsManager.getRegion("street_bg").getRegionWidth(), g.assetsManager.getRegion("street_bg").getRegionHeight());
        lights.setBounds(0, 0, g.assetsManager.getRegion("street_lights").getRegionWidth(), g.assetsManager.getRegion("street_lights").getRegionHeight());
        shadows.setBounds(0, 0, g.assetsManager.getRegion("street_shadows").getRegionWidth(), g.assetsManager.getRegion("street_shadows").getRegionHeight());

        float cycleTime = 6f;
        background.addAction(Actions.forever(Actions.sequence(Actions.color(Color.WHITE, cycleTime), Actions.color(Color.NAVY, cycleTime))));
        lights.addAction(Actions.forever(Actions.sequence(Actions.fadeOut(cycleTime), Actions.fadeIn(cycleTime))));
        shadows.addAction(Actions.forever(Actions.sequence(Actions.fadeOut(cycleTime), Actions.fadeIn(cycleTime))));

        // add and configure Player
        stage.addActor(game.player);
        game.player.getName();

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        /*
        if (actors.get(0).collidesWith(actors.get(1))) {
            actors.get(0).setColor(1, 0, 0, 1);
        } else actors.get(0).setColor(1, 1, 1, 1);
        */
    }
}
