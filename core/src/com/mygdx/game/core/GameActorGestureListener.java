package com.mygdx.game.core;

import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

/**
 * Created by afr on 26.08.16.
 */

public class GameActorGestureListener extends ActorGestureListener {

    public GameActorGestureListener(float longPressDuration) {
        super();
        this.getGestureDetector().setLongPressSeconds(longPressDuration);
    }
}
