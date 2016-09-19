package com.mygdx.game.screens.lobby;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.core.GameScreenUI;
import com.mygdx.game.core.MenuScreen;

/**
 * Created by afr on 08.08.16.
 */

public class PauseMenuScreen extends MenuScreen {
    private Label titleLabel = new Label("CREDITS MENU SCREEN", game.assetsManager.getSkin());
    private Label subTitleLabel = new Label("subtitle ", game.assetsManager.getSkin());
    private TextButton continueButton = new TextButton("Continue", game.assetsManager.getSkin());
    private MainMenuScreen menu;

    public PauseMenuScreen(MainMenuScreen m) {
        super(m.game);
        menu = m;
        addWidgets();
        addListeners();
    }


    protected void addWidgets() {

        // subtable creation and hierarchy
        Table titleTable = new Table();
        Table buttonsTable = new Table();
        Table mainButtons = new Table();
        Table helpButtons = new Table();

        // main cluster table
        clusterTable.add(titleTable).expandX().row();
        clusterTable.add(buttonsTable).expand();
        buttonsTable.add(mainButtons).padRight((int)(buttonsTable.getWidth()*0.1));
        buttonsTable.add(helpButtons).bottom().padLeft((int)(buttonsTable.getWidth()*0.1));

        // title contents
        titleTable.defaults().center();
        titleTable.add(titleLabel).row();
        titleTable.add(subTitleLabel).padBottom(100);
        // main buttons contents
        mainButtons.defaults().prefWidth(160).prefHeight(60);
        mainButtons.add(continueButton).row();
    }

    protected void addListeners() {
        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stage.addAction(game.gotoScreenWITHOUTPREPARING(menu, 0.2f, 0.2f, false));
            }
        });
    }
}
