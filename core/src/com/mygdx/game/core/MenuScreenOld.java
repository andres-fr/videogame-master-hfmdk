package com.mygdx.game.core;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.addAction;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by afr on 08.08.16.
 */



public abstract class MenuScreenOld extends GameScreenRaw {
    GameScreenRaw backScreen;
    public AssetsManager assetsManager;


    public MenuScreenOld(GameScreenRaw s) {
        super(s.game);
        backScreen = s;
    }

    public void gotoPauseScreen() {
        // menuScreens are not able to be paused so this method is empty
        return;
    }


    /*
    @Override
    public void render(float delta) {
        super.render(delta);
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && game.secondsWithoutAction(nanoTime())>0.5) {
            gotoBackScreen();
        }
    }

    */


    public void gotoBackScreen() {

        //addAction(gotoScreen(backScreen, 0.2f, 0.2f));
    }

    public void setBackScreen(GameScreenRaw backScreen) {
        this.backScreen = backScreen;
    }


    // WALTRAPADA ZONE
    public void add(Actor a) {

    }

    public Cell defaults() {
        return new Table().defaults();
    }

    public void addAction(Action action) {

    }

    public Action gotoScreen(GameScreenRaw gs, float a, float b) {
        return Actions.fadeIn(0);
    }
}

