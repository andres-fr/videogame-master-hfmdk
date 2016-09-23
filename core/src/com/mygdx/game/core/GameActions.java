package com.mygdx.game.core;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.mygdx.game.MyGame;

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

    // THE VOID ACTION
    public Action voidAction = Actions.delay(0);

    // ACTIONS INVOLVING ALPHA
    public Action alphaHill(float initOut, float fadeIn, float hill, float fadeOut, float endOut) {
        return Actions.sequence(fadeOut(initOut), fadeIn(fadeIn), fadeIn(hill), fadeOut(fadeOut), fadeOut(endOut));
    }

    // SWITCHING SCREENS:




    private void gotoScreenCore(GameScreenUI nextScreen, float fadeIn, boolean disposeCurrent) {
        if (disposeCurrent && game.getCurrentScreen() != null) game.getCurrentScreen().dispose();
        game.setScreenINSECURE(nextScreen, "imSureOfWhatImDoing");
        nextScreen.stage.addAction(Actions.sequence(Actions.fadeOut(0), Actions.fadeIn(fadeIn)));
    }


    public Action gotoScreenWithSameAssets(final GameScreenUI gs, float fadeout, final float fadein, final boolean disposeCurrent) {
        if (game.assetsManager.getCurrentAtlasLabel() != gs.neededAsset) {
            throw new RuntimeException("trying to use gotoScreenWithSameAssets between 2 screens with different assets: " +
                    game.getCurrentScreen().neededAsset + ", " + gs.neededAsset);
        } // else...
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                //game.getCurrentScreen().background.clearListeners(); //!!!!!!!!!!!!!!!!
            }
        };
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                gotoScreenCore(gs, fadein, disposeCurrent);
            }
        };
        return Actions.sequence(Actions.run(r1), Actions.fadeOut(fadeout), Actions.run(r2));
    }

    public Action gotoNewScreen(final Class<? extends GameScreenUI> screenClass, final Object[] consParameters,
                                final float fadeout, final float fadein) {
        final Class[] parameterClasses = new Class[consParameters.length];
        for (int i = 0; i < consParameters.length; i++) {
            parameterClasses[i] = consParameters[i].getClass();
        }
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                //game.getCurrentScreen().background.clearListeners(); //!!!!!!!!!!!!!
            }
        };
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                try {
                    Constructor cons = ClassReflection.getConstructor(screenClass, parameterClasses);
                    GameScreenUI gs = (GameScreenUI) cons.newInstance(consParameters);
                    gotoScreenCore(gs, fadein, true);
                } catch (ReflectionException e) {
                    System.out.println("MyGame.gotoScreen: something went wrong with the following parameters: " +
                            screenClass + "\n" + consParameters + "\n" + fadeout + "\n" + fadein);
                    e.printStackTrace();
                }
            }
        };
        return Actions.sequence(Actions.run(r1), Actions.fadeOut(fadeout), Actions.run(r2));
    }


    private Action gotoScreenINSECURE(final GameScreenUI gs, final float fadeIn, float fadeOut, String areUSureOfWhatUrDoing) {
        if (areUSureOfWhatUrDoing.equals("imSureOfWhatImDoing")) {
            Runnable r1 = new Runnable() {
                @Override
                public void run() {
                    //game.getCurrentScreen().background.clearListeners(); //!!!!!!!!!!!!!!!!
                }
            };
            Runnable r2 = new Runnable() {
                @Override
                public void run() {
                    gotoScreenCore(gs, fadeIn, false);
                }
            };
            return Actions.sequence(Actions.run(r1), Actions.fadeOut(fadeOut), Actions.run(r2));
        } else throw new RuntimeException("ANTIBUGGING: gotoScreenINSECURE is public, but not expected to be directly used "+
                "(See javadoc for more details). If you still want to use it, type 'imSureOfWhatImDoing' at the String field.");
    }

    public Action pauseGame() {
        game.getPauseMenu().setReturnScreen(game.getCurrentScreen());
        return gotoScreenINSECURE(game.getPauseMenu(), 0.2f, 0.2f, "imSureOfWhatImDoing");
    }

    public Action resumeGame() {
        return gotoScreenINSECURE(game.getPauseMenu().getReturnScreen(), 0.2f, 0.2f, "imSureOfWhatImDoing");
    }

    // ACTIONS INVOLVING MOVEMENT
    public Action moveActorToAnyPoint(GameActor a, float x, float y, Runnable runBeforeMove, Runnable runAfterMove) {
        Vector2 destiny = a.destinyStanding(x, y);
        float time = destiny.dst(a.getXY()) / a.SPEED; // pixels per second
        return Actions.sequence(
                Actions.run(runBeforeMove),
                Actions.moveTo(destiny.x, destiny.y, time, Interpolation.pow2Out),
                Actions.run(runAfterMove));
    }

    public void movePlayerTHENgotoNewScreen(final float xPos, final float yPos, final Class<? extends GameScreenUI> screenClass, final Object[] consParameters,
                                              final float fadeout, final float fadein) {
        game.player.walkToAnyPoint(xPos, yPos);
        game.getCurrentScreen().addActionOnStageAfterActorEndsHisActions(game.player, gotoNewScreen(screenClass, consParameters, fadeout, fadein));
    }
}
