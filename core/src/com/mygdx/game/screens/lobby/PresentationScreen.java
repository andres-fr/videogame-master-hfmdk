package com.mygdx.game.screens.lobby;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGame;
import com.mygdx.game.core.AssetsManager;
import com.mygdx.game.core.GameScreenUI;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.utils.TimeUtils.nanoTime;

/**
 * Created by afr on 08.08.16.
 * This class provides the necessary tools for animating Images with a fade-in and out,
 * and performs the animation in a sequence for every element in the logos array. When
 * it's finished, it skips to the MainMenuScreen.
 * The animations can be skipped by clicking anywhere after MIN_TIME seconds.
 */

public class PresentationScreen extends GameScreenUI {
    // this array can be arbitrarily expanded when needed
    String[] logoNames = {"obama", "korea", "stallman"};
    Array<Image> logos = new Array<Image>();

    private int PREFERRED_WIDTH = (int) (MyGame.WIDTH * 0.3); // height preserves aspect ratio
    private long timeStamp; // needed to keep track of when the current logo started
    private float MIN_TIME = 1f; // a logo can't be skipped until MIN_TIME in seconds passes
    private int currentLogo = 0; // iterates over the logos array
    private int totalLogos = logoNames.length; // ends the iteration over the logos array

    private MainMenuScreen mainMenuScreen;

    public PresentationScreen(MyGame g) {
        super(g, AssetsManager.PREPARE.LOBBY);
        timeStamp = nanoTime();
        mainMenuScreen = new MainMenuScreen(game);
        // fill logos array with the Images with tap listener
        for (String s : logoNames){
            Image i = new Image(g.assetsManager.getCurrentRegion(s));
            i.addListener(new ActorGestureListener(){
                @Override
                public void tap(InputEvent event, float x, float y, int count, int button) {
                    super.tap(event, x, y, count, button);
                    if ((nanoTime() - timeStamp >= MIN_TIME * 1e9) && currentLogo>0) {
                        stage.clear();
                        stage.addAction(createLogoSequence());
                    }
                }
            });
            logos.add(i);
        }
        // start sequence!
        stage.addAction(createLogoSequence());
    }


    /*
     * @param logo the instance of Image to be shown
     * @return the Animation that clears all other logos and displays the given one with the fadeIn/Out
     */
    private Action showLogoAnimated(final Image logo) {
        Runnable r = new Runnable() {
            public void run() {
                for (Actor a : stage.getActors()) a.remove(); // remove is not like clear() see javadocs
                float prefHeight = logo.getHeight() * (PREFERRED_WIDTH / logo.getWidth());
                logo.setSize(PREFERRED_WIDTH, prefHeight);
                logo.setPosition((game.WIDTH-PREFERRED_WIDTH)/2, (game.HEIGHT-prefHeight)/2);
                stage.addActor(logo);
                timeStamp = nanoTime(); // time when logo started
                currentLogo++;
            }
        };
        // retrieve wanted action
        return parallel(Actions.run(r), game.actions.alphaHill(0, 0.5f, 1, 1.5f, 1));
    }

    /**
     * @return a sequence of faded in/out logos (starting from currentLogo) that ends switching to the nextScreen.
     */
    private Action createLogoSequence() {
        Action[] actionsChain = new Action[(totalLogos - currentLogo) + 1];
        for (int i = 0; i < (totalLogos - currentLogo); i++) { // add logo animations
            actionsChain[i] = showLogoAnimated(logos.get(i+currentLogo));
        }
        actionsChain[totalLogos - currentLogo] = game.actions.gotoScreenWithSameAssets(mainMenuScreen, 0, 0.2f, true);
        return (Actions.sequence(actionsChain));
    }
}
