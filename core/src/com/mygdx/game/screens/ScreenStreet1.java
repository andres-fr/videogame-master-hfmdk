package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.MyGame;
import com.mygdx.game.core.GameScreen;
import com.mygdx.game.core.WalkZone;


/**
 * Created by afr on 23.08.16.
 */

public class ScreenStreet1 extends GameScreen {

    public ScreenStreet1(MyGame g) {
        super(g, "street_bg", "street_shadows", "street_lights");
        setActorScale(0.7f, 0.2f);
        setTravellingMargin(0.33f);
        float cycleTime = 6f;
        background.addAction(Actions.forever(Actions.sequence(Actions.color(Color.WHITE, cycleTime), Actions.color(Color.NAVY, cycleTime))));
        lights.addAction(Actions.forever(Actions.sequence(Actions.fadeOut(cycleTime), Actions.fadeIn(cycleTime))));
        shadows.addAction(Actions.forever(Actions.sequence(Actions.fadeOut(cycleTime), Actions.fadeIn(cycleTime))));

        addWalkzoneScaled(new int[]{1,9,6697,4,6696,21,3887,641,3733,645,3731,320,1935,352,1917,321,1829,320,1823,362,1445,369,
                1186,335,1139,369,1146,453,1028,465,966,443,903,443,825,464,788,495,736,491,757,337,306,238,72,249,3,270,0,1035});

        // add and configure Player initial pos
        stage.addActor(g.player);
        g.player.setScale(0.5f);
        Vector2 v2 = g.player.destinyCentered(g.WIDTH/2, g.HEIGHT/2);
        g.player.setPosition(v2.x, v2.y);

    }


    @Override
    public void render(float delta) {
        super.render(delta);
        //resize player (maybe not the best place??)
        game.player.setScale(getScaleFromStageY(game.player.getY()));

    }


    @Override
    public void backgroundTouchedDown(float x, float y) {
        game.player.walkTo(x, y);
    }
}
