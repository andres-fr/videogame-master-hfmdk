package com.mygdx.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.MyGame;

/**
 * Created by afr on 31.08.16.
 */

public class GameScreenExpanded extends GameScreenRaw {
    public GameScreenExpanded(MyGame g) {
        super(g);
    }

    public void changeScreen(Runnable r, float fadeOutTime, float fadeInTime) {
        game.stage.addAction(Actions.sequence(Actions.fadeOut(fadeOutTime), Actions.run(r), Actions.fadeIn(fadeInTime)));
    }

    private Runnable changeToTestScreen = new Runnable() {
        @Override
        public void run() {
            cleanScreen();
            currentState = STATES.UI;
            setBackgroundSuite("testImg", "testImg", "testImg");
        }
    };

    private Runnable changeToGameScreen= new Runnable() {
        @Override
        public void run() {
            currentState = STATES.GAMEPLAY;
            cleanScreen();
            setBackgroundSuite("street_bg", "street_shadows", "street_lights");
            addBackgroundListener();
            setActorScale(0.7f, 0.025f);
            float cycleTime = 6f;
            background.addAction(Actions.forever(Actions.sequence(Actions.color(Color.WHITE, cycleTime), Actions.color(Color.NAVY, cycleTime))));
            lights.addAction(Actions.forever(Actions.sequence(Actions.fadeOut(cycleTime), Actions.fadeIn(cycleTime))));
            shadows.addAction(Actions.forever(Actions.sequence(Actions.fadeOut(cycleTime), Actions.fadeIn(cycleTime))));
            addWalkzoneScaled(new int[]{1, 9, 6697, 4, 6696, 21, 3887, 641, 3733, 645, 3731, 320, 1935, 352, 1917, 321, 1829, 320, 1823, 362, 1445, 369,
                    1186, 335, 1139, 369, 1146, 453, 1028, 465, 966, 443, 903, 443, 825, 464, 788, 495, 736, 491, 757, 337, 306, 238, 72, 249, 3, 270, 0, 1035});

            // add and configure Player initial pos
            game.stage.addActor(game.player);
            game.player.setScale(0.5f);
            Vector2 v2 = game.player.destinyCentered(game.WIDTH / 2, game.HEIGHT / 2);
            game.player.setPosition(v2.x, v2.y);
        }
    };


    @Override
    public void render(float delta) {
        super.render(delta);
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            changeScreen(changeToTestScreen, 1, 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            changeScreen(changeToGameScreen, 1, 1);
        }
    }
}
