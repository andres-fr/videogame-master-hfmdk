package com.mygdx.game.core;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGame;
import com.mygdx.game.actors.OmnipresentInvisibleActor;

/**
 * Created by afr on 22.09.16.
 */

public class GameStage extends Stage {
    public MyGame game;
    public GameStage(MyGame g) {
        super(new FitViewport(MyGame.WIDTH, MyGame.HEIGHT), g.batch);
        game = g;
        ((OrthographicCamera) getCamera()).setToOrtho(false, MyGame.WIDTH, MyGame.HEIGHT);
        setDebugAll(MyGame.DEBUG);
    }

    /**
     * like the default method, calls unfocusAll. Then removes all actions, and all listeners.
     * But THEN, removes all actors EXCEPT those of type OmnipresentInvisibleActor
     */
    @Override
    public void clear() {
        unfocusAll();
        getRoot().clearActions();
        getRoot().clearListeners();
        removeActorsButNotListenersNorActions();
    }

    /**
     * removes all children actors except the ones of type OmnipresentInvisibleActor.
     * For the complete removal of all actors use clear()
     */
    public void removeActorsButNotListenersNorActions() {
        for (Actor a : getActors()) {
            if (a.getClass() != OmnipresentInvisibleActor.class){
                a.remove();
            }
        }
    }

}
