package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGame;
import com.mygdx.game.core.GameScreen;
import com.mygdx.game.screens.main_menu.MainMenuScreen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.addAction;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.removeActor;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.show;
import static com.badlogic.gdx.utils.TimeUtils.nanoTime;

/**
 * Created by afr on 08.08.16.
 * This class provides the necessary tools for animating Images with a fade-in and out,
 * and performs the animation in a sequence for every element in the logos array. When
 * it's finished, it skips to the MainMenuScreen.
 * The animations can be skipped by clicking anywhere after MIN_TIME seconds.
 */

public class PresentationScreen extends GameScreen {
    // this array can be arbitrarily expanded when needed
    Image[] logos = {new Image(new Texture(Gdx.files.internal("logos/obama.png"))),
            new Image(new Texture(Gdx.files.internal("logos/korea.jpg"))),
            new Image(new Texture(Gdx.files.internal("logos/stallman.jpeg")))};
    GameScreen nextScreen; // typically the MainMenu
    private int PREFERRED_WIDTH = 500; // height preserves aspect ratio
    private long timeStamp; // needed to keep track of when the current logo started
    private float MIN_TIME = 1f; // a logo can't be skipped until MIN_TIME in seconds passes
    private int currentLogo = 0; // iterates over the logos array
    private int totalLogos = logos.length; // ends the iteration over the logos array


    public PresentationScreen(MyGame g) {
        super(g, Color.BLACK);
        //defaults().prefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
        nextScreen = new MainMenuScreen(g);
        timeStamp = nanoTime();
        addAction(createLogoSequence());

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // check if the user wants to skip the logo animation
        if ((Gdx.input.isTouched()) && (nanoTime() - timeStamp >= MIN_TIME * 1e9) && currentLogo>0) {
            clearActions();
            addAction(createLogoSequence());// the sequence will be shorter
        }
    }

    /**
     *
     * @param logo the instance of Image to be shown
     * @return the Animation that clears all other logos and displays the given one with the fadeIn/Out
     */
    private Action showLogoAnimated(final Image logo) {
        Runnable r = new Runnable() {
            public void run() {
                clearChildren();
                float prefHeight = logo.getHeight() * (PREFERRED_WIDTH / logo.getWidth());
                defaults().prefSize(PREFERRED_WIDTH, (int) prefHeight);
                add(logo);
                timeStamp = nanoTime(); // time when logo started
                currentLogo++;
            }
        };
        // retrieve wanted action
        return parallel(run(r), alphaHill(0, 0.5f, 1, 1.5f, 1));
    }

    /**
     *
     * @return a sequence of faded in/out logos (starting from currentLogo) that ends switching to the nextScreen.
     */
    private Action createLogoSequence() {
        SequenceAction mainSequence = new SequenceAction();
        // build main sequence
        if (currentLogo == 0) mainSequence.addAction(alphaHill(1, 0, 0, 0, 0)); // initial fade-out time 1 second
        for (int i = currentLogo; i<totalLogos; i++) { // add logo animations
            mainSequence.addAction(showLogoAnimated(logos[i]));
        }
        mainSequence.addAction(gotoScreen(nextScreen, 0, 0.5f)); // go to main menu once finished
        return mainSequence;
    }
}
