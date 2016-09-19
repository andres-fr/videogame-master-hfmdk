package com.mygdx.game.actors.chapter1;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.mygdx.game.MyGame;
import com.mygdx.game.core.GameActor;
import com.mygdx.game.screens.chapter1.RoomChapter1Screen;

/**
 * Created by afr on 19.09.16.
 */

public class ApartmentPortal extends GameActor {

    public ApartmentPortal(MyGame g) {
        super(g, 1460, 240, 100, 220, true);
        addListener(new ActorGestureListener(){
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                getStage().addAction(game.gotoScreen(new RoomChapter1Screen(game), 0.5f, 0.5f, true));
            }
        });
    }
}
