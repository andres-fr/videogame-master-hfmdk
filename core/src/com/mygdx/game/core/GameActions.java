package com.mygdx.game.core;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.mygdx.game.MyGame;
import com.mygdx.game.actors.Player;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;

/**
 * Created by afr on 16.09.16.
 */

public class GameActions {
    MyGame game;

    public GameActions(MyGame g) {
        game = g;
    }



    // ACTIONS INVOLVING ALPHA
    public Action alphaHill(float initOut, float fadeIn, float hill, float fadeOut, float endOut) {
        return Actions.sequence(fadeOut(initOut), fadeIn(fadeIn), fadeIn(hill), fadeOut(fadeOut), fadeOut(endOut));
    }


    // ACTIONS INVOLVING PLAYER
    public Action walkPlayerToAnyPoint(float x, float y) {
        Vector2 destiny = game.player.destinyStanding (x, y);
        float time = destiny.dst(game.player.getXY())/Player.SPEED;
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                game.player.setState(Player.STATES.LEFT);
            }
        };
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                game.player.setState(Player.STATES.IDLE);
            }
        };
        return Actions.sequence(
                Actions.run(r1),
                Actions.moveTo(destiny.x, destiny.y, time, Interpolation.pow2Out),
                Actions.run(r2));
    }


    // SWITCHING SCREENS:



    private void gotoScreenCore(GameScreenUI nextScreen, float fadeIn, boolean disposeCurrent) {
        if (disposeCurrent && game.getCurrentScreen()!=null) game.getCurrentScreen().dispose();
        game.setScreenSECURE(nextScreen, "imSureOfWhatImDoing");
        nextScreen.stage.addAction(Actions.sequence(Actions.fadeOut(0), Actions.fadeIn(fadeIn)));
    }


    public Action gotoScreenWithSameAssets(final GameScreenUI gs, float fadeout, final float fadein, final boolean disposeCurrent) {
        if (game.assetsManager.getCurrentAtlasLabel() != gs.neededAsset){
            throw new RuntimeException("trying to use gotoScreenWithSameAssets between 2 screens with different assets: " +
                    game.getCurrentScreen().neededAsset + ", " + gs.neededAsset);
        } // else...
        Runnable r = new Runnable() {
            @Override
            public void run() {
                gotoScreenCore(gs, fadein, disposeCurrent);
            }
        };
        return Actions.sequence(Actions.fadeOut(fadeout), Actions.run(r));
    }

    public Action gotoNewScreen(final Class<? extends GameScreenUI> screenClass, final Object[] consParameters,
                                final float fadeout, final float fadein) {
        final Class[] parameterClasses = new Class[consParameters.length];
        for (int i= 0; i<consParameters.length; i++){
            parameterClasses[i] = consParameters[i].getClass();
        }
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    Constructor cons = ClassReflection.getConstructor(screenClass, parameterClasses);
                    GameScreenUI gs = (GameScreenUI) cons.newInstance(consParameters);
                    gotoScreenCore(gs, fadein, true);
                } catch (ReflectionException e) {
                    System.out.println("MyGame.gotoScreen: something went wrong with the following parameters: "+
                            screenClass + "\n" + consParameters + "\n" + fadeout + "\n" + fadein);
                    e.printStackTrace();
                }
            }
        };
        return Actions.sequence(Actions.fadeOut(fadeout), Actions.run(r));
    }

    public Action movePlayerTHENgotoNewScreen(float xPos, float yPos, final Class<? extends GameScreenUI> screenClass, final Object[] consParameters,
                                            final float fadeout, final float fadein){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                game.getCurrentScreen().background.clearListeners();
            }
        };
        return Actions.sequence(
                walkPlayerToAnyPoint(xPos, yPos),
                Actions.run(r),
                gotoNewScreen(screenClass, consParameters, fadeout, fadein));
    }
}
