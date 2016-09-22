package com.mygdx.game.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.game.MyGame;

import java.util.Random;

import static com.badlogic.gdx.utils.TimeUtils.nanoTime;

/**
 * Created by afr on 22.09.16.
 */

public class OmnipresentInvisibleActor extends Actor {

    MyGame game;
    long timeStamp;
    long randomID = (new Random()).nextLong();

    public OmnipresentInvisibleActor(MyGame g) {
        super();
        game = g;
        setVisible(false);
        timeStamp = nanoTime();
        if(MyGame.DEBUG) System.out.println("created omnipresentInvisibleActor with ID: "+randomID);
    }



    @Override
    public void act(float delta) {
        super.act(delta);
        if  (true && (nanoTime()-timeStamp)>1e9){ // game.DEBUG
            timeStamp = nanoTime();
            System.out.println(" Random ID for the OmnipresentInvisibleActor: " + randomID);

        }
    }
}
