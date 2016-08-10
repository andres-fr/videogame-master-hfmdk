package com.mygdx.game.screens.main_menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.MyGame;
import com.mygdx.game.core.AssetsLoader;
import com.mygdx.game.core.GameScreen;
import com.mygdx.game.screens.chapter1.ChapterOneIntroduction;

/**
 * Created by afr on 08.08.16.
 */

public class MainMenuScreen extends GameScreen {
    // size config
    public static int PREF_MENU_WIDTH = 1100;
    public static int PREF_MENU_HEIGHT = 600;

    // related menu screens
    private GameScreen optionsScreen = new OptionsMenuScreen(this);
    private GameplayMenuScreen gameplayScreen = new GameplayMenuScreen(this);
    private CreditsScreen creditsScreen = new CreditsScreen(this);
    private LoadSaveMenuScreen loadSaveMenuScreen = new LoadSaveMenuScreen(this);

    // menu widgets
    private Label titleLabel = new Label("GAME TITLE", AssetsLoader.uiSkin);
    private Label versionLabel = new Label("version "+ MyGame.VERSION, AssetsLoader.uiSkin);
    private TextButton continueButton = new TextButton("Continue Game", AssetsLoader.uiSkin);
    private TextButton newGameButton = new TextButton("New Game", AssetsLoader.uiSkin);
    private TextButton loadSaveButton = new TextButton("Load/Save Game", AssetsLoader.uiSkin);
    private TextButton exitButton = new TextButton("Exit", AssetsLoader.uiSkin);
    private TextButton optionsButton = new TextButton("Options", AssetsLoader.uiSkin);
    private TextButton gameplayButton = new TextButton("Gameplay", AssetsLoader.uiSkin);
    private TextButton creditsButton = new TextButton("Credits", AssetsLoader.uiSkin);

    public MainMenuScreen(MyGame g) {
        super(g);
        addWidgets();
        addListeners();
    }


    private void addWidgets() {

        // subtable creation and hierarchy
        Table clusterTable = new Table();
        Table titleTable = new Table();
        Table buttonsTable = new Table();
        Table mainButtons = new Table();
        Table helpButtons = new Table();

        //cluster config
        defaults().prefSize(PREF_MENU_WIDTH, PREF_MENU_HEIGHT).center();

        // main cluster table
        add(clusterTable);
        clusterTable.add(titleTable).expandX().row();
        clusterTable.add(buttonsTable).expand();
        buttonsTable.add(mainButtons).padRight(150);
        buttonsTable.add(helpButtons).bottom().padLeft(150);

        // title contents
        titleTable.defaults().center();
        titleTable.add(titleLabel).row();
        titleTable.add(versionLabel).padBottom(150);
        // main buttons contents
        mainButtons.defaults().prefWidth(160).prefHeight(60);
        mainButtons.add(continueButton).row();
        mainButtons.add(newGameButton).row();
        mainButtons.add(loadSaveButton).row();
        mainButtons.add(exitButton).row();
        // help buttons contents
        helpButtons.defaults().prefWidth(140).prefHeight(40);
        helpButtons.add(optionsButton).row();
        helpButtons.add(gameplayButton).row();
        helpButtons.add(creditsButton);
    }

    private void addListeners() {

        newGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startNewGame();
            }
        });

        loadSaveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                addAction(gotoScreen(loadSaveMenuScreen, 0.2f, 0.2f));
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                addAction(gotoScreen(optionsScreen, 0.2f, 0.2f));
            }
        });

        gameplayButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                addAction(gotoScreen(gameplayScreen, 0.2f, 0.2f));
            }
        });

        creditsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                addAction(gotoScreen(creditsScreen, 0.2f, 0.2f));
            }
        });

    }

    private void startNewGame() {
        addAction(gotoScreen(new ChapterOneIntroduction(this), 0.2f, 0.2f));
    }
}
