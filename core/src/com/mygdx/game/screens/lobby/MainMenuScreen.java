package com.mygdx.game.screens.lobby;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.MyGame;
import com.mygdx.game.core.AssetsManager;
import com.mygdx.game.core.GameScreenUI;
import com.mygdx.game.core.MenuScreen;
import com.mygdx.game.screens.chapter1.StreetChapter1Screen;

/**
 * Created by afr on 08.08.16.
 */

public class MainMenuScreen extends MenuScreen {

    // related menu screens
    private OptionsMenuScreen optionsScreen = new OptionsMenuScreen(this);
    private GameplayMenuScreen gameplayScreen = new GameplayMenuScreen(this);
    private CreditsScreen creditsScreen = new CreditsScreen(this);
    private LoadSaveMenuScreen loadSaveMenuScreen = new LoadSaveMenuScreen(this);

    // MAIN CONTAINER for everything else
    // menu widgets
    private Label titleLabel = new Label("GAME TITLE", game.assetsManager.getSkin());
    private Label versionLabel = new Label("version "+ MyGame.VERSION, game.assetsManager.getSkin());
    private TextButton continueButton = new TextButton("Continue Game", game.assetsManager.getSkin());
    private TextButton newGameButton = new TextButton("New Game", game.assetsManager.getSkin());
    private TextButton loadSaveButton = new TextButton("Load/Save Game", game.assetsManager.getSkin());
    private TextButton exitButton = new TextButton("Exit", game.assetsManager.getSkin());
    private TextButton optionsButton = new TextButton("Options", game.assetsManager.getSkin());
    private TextButton gameplayButton = new TextButton("Gameplay", game.assetsManager.getSkin());
    private TextButton creditsButton = new TextButton("Credits", game.assetsManager.getSkin());

    public MainMenuScreen(MyGame g) {
        super(g);
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
        titleTable.add(versionLabel).padBottom(100);
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
                stage.addAction(game.gotoScreen(new StreetChapter1Screen(game), 0.2f, 0.5f, true));
            }
        });

        loadSaveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stage.addAction(game.gotoScreen(loadSaveMenuScreen, 0.2f, 0.2f, false));
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.exit();
            }
        });

        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stage.addAction(game.gotoScreen(optionsScreen, 0.2f, 0.2f, false));
            }
        });

        gameplayButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stage.addAction(game.gotoScreen(gameplayScreen, 0.2f, 0.2f,false));
            }
        });

        creditsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stage.addAction(game.gotoScreen(creditsScreen, 0.2f, 0.2f, false));
            }
        });
    }


    @Override
    public void dispose() {
        super.dispose();
        optionsScreen.dispose();
        creditsScreen.dispose();
        gameplayScreen.dispose();
        loadSaveMenuScreen.dispose();
    }
}
