package com.mygdx.game.screens.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.MyGame;
import com.mygdx.game.core.GameScreen;
import com.mygdx.game.core.MenuScreen;

/**
 * Created by afr on 08.08.16.
 */

public class MainMenuScreen extends MenuScreen {
    // size config
    public static int PREF_MENU_WIDTH = (int)(MyGame.WIDTH*0.9);
    public static int PREF_MENU_HEIGHT = (int)(MyGame.HEIGHT*0.9);

    // related menu screens
    private GameScreen optionsScreen = new OptionsMenuScreen(this);
    private GameplayMenuScreen gameplayScreen = new GameplayMenuScreen(this);
    private CreditsScreen creditsScreen = new CreditsScreen(this);
    private LoadSaveMenuScreen loadSaveMenuScreen = new LoadSaveMenuScreen(this);

    // menu widgets
    private Label titleLabel = new Label("GAME TITLE", game.assetsManager.getSkin("uiskin"));
    private Label versionLabel = new Label("version "+ MyGame.VERSION, game.assetsManager.getSkin("uiskin"));
    private TextButton continueButton = new TextButton("Continue Game", game.assetsManager.getSkin("uiskin"));
    private TextButton newGameButton = new TextButton("New Game", game.assetsManager.getSkin("uiskin"));
    private TextButton loadSaveButton = new TextButton("Load/Save Game", game.assetsManager.getSkin("uiskin"));
    private TextButton exitButton = new TextButton("Exit", game.assetsManager.getSkin("uiskin"));
    private TextButton optionsButton = new TextButton("Options", game.assetsManager.getSkin("uiskin"));
    private TextButton gameplayButton = new TextButton("Gameplay", game.assetsManager.getSkin("uiskin"));
    private TextButton creditsButton = new TextButton("Credits", game.assetsManager.getSkin("uiskin"));

    public MainMenuScreen(GameScreen s) {
        super(s);
        addWidgets();
        addListeners();
    }


    @Override
    public void gotoBackScreen() {
        return;
    }

    protected void addWidgets() {

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
        buttonsTable.add(mainButtons).padRight((int)(buttonsTable.getWidth()*0.1));
        buttonsTable.add(helpButtons).bottom().padLeft((int)(buttonsTable.getWidth()*0.1));

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

    protected void addListeners() {
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
        //addAction(gotoScreen(new ChapterOneIntroduction(this), 0.2f, 0.2f));
    }
}
