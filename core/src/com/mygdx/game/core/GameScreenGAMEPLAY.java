package com.mygdx.game.core;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.game.MyGame;


/**
 * Created by afr on 15.09.16.
 * This class has all the functionalities of GameScreenUI, plus some more:
 * -The background is touchable and has a touchdown listener which calls the Player.walkTo method.
 * -In render, the camera x-position and the Player's size are also updated
 */

public class GameScreenGAMEPLAY extends GameScreenUI {
    // THIS CONSTANTS ARE A POTENTIAL BUG SOURCE: revise in case player size glitches!!!
    // see also AddGameplayFunctionality and getScaleFromStageY
    private static final double EPSILON = 0.0001f;
    private static final double BIG_NEGATIVE = -1000000;
    protected float playerSizeDown;
    protected float playerSizeUp;
    protected double playerScaleTensor ;

    // This constants are derivated from the variables above and are
    // needed in order to spare some computation overhead
    private double expTensor;


    public GameScreenGAMEPLAY(MyGame g, AssetsManager.PREPARE prepareAsset, float downSize, float upSize, double tensor, String bgrnd) {
        super(g, prepareAsset, bgrnd);
        addGameplayFunctionality(downSize, upSize, tensor);

    }

    public GameScreenGAMEPLAY(MyGame g, AssetsManager.PREPARE prepareAsset, float downSize, float upSize, double tensor, String bgrnd, String shdw, String lghts) {
        super(g, prepareAsset, bgrnd, shdw, lghts);
        addGameplayFunctionality(downSize, upSize, tensor);
    }

    /**
     * if screen is of type GAMEPLAY, the background is touchable and activates Player.walkTo() when clicked
     */
    private void addGameplayFunctionality(float downSize, float upSize, double tensor) {
        if (tensor < 0 || tensor > 1) throw new RuntimeException("tensor ("+tensor+") must be between 0 and 1!");
        if (downSize<0 || upSize < 0) throw new RuntimeException("both downSize ("+downSize+ ") and upSize ("+tensor+ ") must be non-negative!");
        playerSizeDown = downSize;
        playerSizeUp = upSize;
        playerScaleTensor = tensor;
        // Explanation: A is wanted to be between 0 and 1. But the formula using a expects it
        // to be -inf <= A < upSize. Therefore the log generates -inf<=A<=1, and the multiplication
        // constraints it to -inf<=A<(upsize-EPSILON)
        double smallest = Math.min(upSize, downSize);
        expTensor = (tensor==0)? BIG_NEGATIVE : Math.log(tensor*Math.E) * (smallest-EPSILON);
        //
        background.setTouchable(Touchable.enabled);
        background.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                walkPlayerToWalkZone(x, y);
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        //useful for debugging purposes
        // addWalkzoneScaled(new int[]{0, 500, 100000000, 500 , 100000000, 100000000, 0, 100000000}); // testing wz

    }

    /**
     * this method updates the camera x-position, and is called by render(delta) when the
     * type of the screen is gameplay
     * @param delta just to be passed in render(delta)
     */
    private void cameraFollowsPlayer(float delta) {
        float playerX = game.player.getCenter().x;
        float camX = stage.getCamera().position.x;
        camX += (playerX - camX) * game.player.SPEED * game.CAM_SPEED_RATIO  * delta;
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

    private float getScaleFromStageY(float yPos) {
        double formula = ((playerSizeDown - expTensor) * Math.pow((playerSizeDown - expTensor) /
                (playerSizeUp - expTensor), (-yPos / background.getHeight())) + expTensor);
        return (float) formula;
    }

    public void walkPlayerToWalkZone(float x, float y) {
        for (WalkZone wz : getWalkZones()) {
            if (wz.contains(x, y)){
                game.player.walkTo(x, y);
                break;
            }
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        cameraFollowsPlayer(delta);
        game.player.setScale(getScaleFromStageY(game.player.getY()));
    }
}











/*
    private float getFocalScaleFromStageY(float yPos) {
        float b = 0.9f;
        float c = 4f;
        float y1 = 1;
        return b*y1 / ((yPos / background.getHeight())*(c-1)+y1);
    }

    private float getLinearScaleFromStageY(float yPos) {
        return (yPos / background.getHeight()) * (playerSizeUp - playerSizeDown) + playerSizeDown;
    }

    private float getExponentialScaleFromStageY(float yPos) {
        float a = 0.2f;
        float b = 0.9f;
        float h_m = 1000f;
        float y_1 = 1;
        double y_r = -y_1 / ((Math.log(h_m / b) - Math.log(h_m / a)));
        double formula = h_m * Math.exp(-(yPos / background.getHeight()+y_r*Math.log(h_m/b)) / y_r);
        return (float)formula;
    }

*/