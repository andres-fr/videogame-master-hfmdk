package com.mygdx.game.screens.menus;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.core.GameScreenUI;

/**
 * Created by afr on 08.08.16.
 */

public class CreditsScreen extends GameScreenUI {
    private Label titleLabel = new Label("OPTIONS MENU SCREEN", game.assetsManager.getSkin());
    private Label subTitleLabel = new Label("subtitle ", game.assetsManager.getSkin());
    private TextButton continueButton = new TextButton("Continue", game.assetsManager.getSkin());
    private MainMenuScreen menu;

    public CreditsScreen(MainMenuScreen m) {
        super(m.game, "cage");
        menu = m;
        addWidgets();
        addListeners();
    }


    protected void addWidgets() {

        // subtable creation and hierarchy
        Table clusterTable = new Table();
        Table titleTable = new Table();
        Table buttonsTable = new Table();
        Table mainButtons = new Table();
        Table helpButtons = new Table();

        //cluster config
        clusterTable.defaults().prefSize(MainMenuScreen.PREF_MENU_WIDTH, MainMenuScreen.PREF_MENU_HEIGHT).center();

        // main cluster table
        stage.addActor(clusterTable);
        clusterTable.add(titleTable).expandX().row();
        clusterTable.add(buttonsTable).expand();
        buttonsTable.add(mainButtons);//.padRight(150);
        //buttonsTable.add(helpButtons).bottom().padLeft(150);

        // title contents
        titleTable.defaults().center();
        titleTable.add(titleLabel).row();
        titleTable.add(subTitleLabel).padBottom(150);
        // main buttons contents
        mainButtons.defaults().prefWidth(160).prefHeight(60);
        mainButtons.add(continueButton).row();
    }

    protected void addListeners() {
        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.gotoScreen(menu, "lobby", 0.2f, 0.2f);
            }
        });
    }
}
