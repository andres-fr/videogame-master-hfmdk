package com.mygdx.game.screens.lobby;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGame;
import com.mygdx.game.core.AssetsManager;
import com.mygdx.game.core.GameScreenUI;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
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

    /*
    Image[] logos = {new Image(new Texture(Gdx.files.internal("logos/obama.png"))),
            new Image(new Texture(Gdx.files.internal("logos/korea.jpg"))),
            new Image(new Texture(Gdx.files.internal("logos/stallman.jpeg")))};
            */
    private int PREFERRED_WIDTH = (int) (MyGame.WIDTH * 0.3); // height preserves aspect ratio
    private long timeStamp; // needed to keep track of when the current logo started
    private float MIN_TIME = 1f; // a logo can't be skipped until MIN_TIME in seconds passes
    private int currentLogo = 0; // iterates over the logos array
    private int totalLogos = logoNames.length; // ends the iteration over the logos array


    Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
    Texture tex = new Texture(p);
    TextureRegion rect = new TextureRegion(tex, 16, 16, 1, 1);


    public PresentationScreen(MyGame g) {
        super(g);
        timeStamp = nanoTime();
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
                for (Actor a : stage.getActors()) a.remove();
                float prefHeight = logo.getHeight() * (PREFERRED_WIDTH / logo.getWidth());
                logo.setSize(PREFERRED_WIDTH, prefHeight);
                logo.setPosition((game.WIDTH-PREFERRED_WIDTH)/2, (game.HEIGHT-prefHeight)/2);
                stage.addActor(logo);
                timeStamp = nanoTime(); // time when logo started
                currentLogo++;
            }
        };
        // retrieve wanted action
        return parallel(Actions.run(r), alphaHill(0, 0.5f, 1, 1.5f, 1));
    }

    private Action alphaHill(float initOut, float fadeIn, float hill, float fadeOut, float endOut) {
        return Actions.sequence(fadeOut(initOut), fadeIn(fadeIn), fadeIn(hill), fadeOut(fadeOut), fadeOut(endOut));
    }

    /**
     * @return a sequence of faded in/out logos (starting from currentLogo) that ends switching to the nextScreen.
     */
    private Action createLogoSequence() {
        SequenceAction mainSequence = new SequenceAction();
        // build main sequence
        if (currentLogo == 0) mainSequence.addAction(alphaHill(1, 0, 0, 0, 0)); // initial fade-out time 1 second
        for (int i = currentLogo; i<totalLogos; i++) { // add logo animations
            mainSequence.addAction(showLogoAnimated(logos.get(i)));
        }
        mainSequence.addAction(game.gotoScreenWITHOUTPREPARING(new MainMenuScreen(game), 0, 0.5f, true)); // go to main menu once finished
        return mainSequence;
    }
}