package com.mygdx.game.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.mygdx.game.MyGame;
import com.mygdx.game.core.GameActor;
import com.mygdx.game.core.GameScreenUI;
import com.mygdx.game.screens.chapter1.RoomChapter1Screen;

/**
 * Created by afr on 19.09.16.
 */

public class Door extends GameActor {

    public Class nextScreenClass;

    public Door(MyGame g, int posX, int posY, int width, int height, final Class<? extends GameScreenUI> screenClass) {
        super(g, posX, posY, width, height, true);
        nextScreenClass = screenClass;
        final Door thisDoor = this;
        addListener(new ActorGestureListener(){
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.player.walkToDoorAndCross(thisDoor);
            }
        });
    }
}
