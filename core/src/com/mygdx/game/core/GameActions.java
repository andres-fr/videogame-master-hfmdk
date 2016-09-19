package com.mygdx.game.core;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Created by afr on 16.09.16.
 */

public abstract class GameActions {

    private GameActions() {}

    public static Action fadeOutFadeIn(float fadeOutTime, float fadeInTime) {
        return Actions.sequence(Actions.fadeOut(fadeOutTime), Actions.fadeIn(fadeInTime));
    }

    public static Action fadeOutRunFadeIn(float fadeOutTime, Runnable r, float fadeInTime) {
        return Actions.sequence(Actions.fadeOut(fadeOutTime), Actions.run(r), Actions.fadeIn(fadeInTime));
    }
}
