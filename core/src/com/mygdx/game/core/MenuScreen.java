package com.mygdx.game.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGame;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.addAction;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.badlogic.gdx.utils.TimeUtils.nanoTime;

/**
 * Created by afr on 08.08.16.
 */

public abstract class MenuScreen extends GameScreen {
    GameScreen backScreen;
    public AssetsManager assetsManager;


    public MenuScreen(GameScreen s) {
        super(s.game, "", "", "");
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

    public void setBackScreen(GameScreen backScreen) {
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

    public Action gotoScreen(GameScreen gs, float a, float b) {
        return Actions.fadeIn(0);
    }
}

