package com.mygdx.game.core;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGame;

/**
 * Created by afr on 15.09.16.
 * This class has all the functionalities of GameScreenUI, plus some more:
 * -The background is touchable and has a touchdown listener which calls the Player.walkTo method.
 * -In render, the camera x-position and the Player's size are also updated
 */

public class GameScreenGAMEPLAY extends GameScreenUI {
    protected float downScale, upScale;

    public GameScreenGAMEPLAY(MyGame g, float down_scale, float up_scale, String bgrnd) {
        super(g, bgrnd);
        addGameplayFunctionality(down_scale, up_scale);

    }

    public GameScreenGAMEPLAY(MyGame g, float down_scale, float up_scale, String bgrnd, String shdw, String lghts) {
        super(g, bgrnd, shdw, lghts);
        addGameplayFunctionality(down_scale, up_scale);
    }

    /**
     * if screen is of type GAMEPLAY, the background is touchable and activates Player.walkTo() when clicked
     */
    private void addGameplayFunctionality(float down_scale, float up_scale) {
        downScale = down_scale;
        upScale = up_scale;
        background.setTouchable(Touchable.enabled);
        background.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.player.walkTo(x, y);
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }





    /**
     * this method updates the camera x-position, and is called by render(delta) when the
     * type of the screen is gameplay
     * @param delta just to be passed in render(delta)
     */
    private void cameraFollowsPlayer(float delta) {
        float playerX = game.player.getCenter().x;
        float camX = stage.getCamera().position.x;
        camX += (playerX - camX) * game.CAM_SPEED * delta;
        // move cam
        stage.getCamera().position.x = camX;
        // prevent cam to override width bounds
        if (stage.getCamera().position.x < MyGame.WIDTH / 2){
            stage.getCamera().position.x = MyGame.WIDTH / 2;
        }
        if (stage.getCamera().position.x > background.getWidth() - MyGame.WIDTH / 2) {
            stage.getCamera().position.x = background.getWidth() - MyGame.WIDTH / 2;
        }
    }

    /**
     * @param yPos y position of Player actor
     * @return the scale factor for Player depending on its position and the up/downScale ratio (linear interp)
     */
    private float getScaleFromStageY(float yPos) {
        float yRel = (yPos / background.getHeight()) * (upScale - downScale) + downScale;
        return yRel;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        cameraFollowsPlayer(delta);
        game.player.setScale(getScaleFromStageY(game.player.getY()));
    }
}
