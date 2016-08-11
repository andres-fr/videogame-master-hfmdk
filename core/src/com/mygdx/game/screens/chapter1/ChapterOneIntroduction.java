package com.mygdx.game.screens.chapter1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGame;
import com.mygdx.game.core.AssetsLoader;
import com.mygdx.game.core.GameScreen;
import com.mygdx.game.core.TintedImage;
import com.mygdx.game.screens.menus.MainMenuScreen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.color;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.badlogic.gdx.utils.TimeUtils.nanoTime;

/**
 * Created by afr on 10.08.16.
 */

public class ChapterOneIntroduction extends GameScreen {
    String[] files = {"village.png", "sea.jpg", "city-windows.png"};
    Array<Drawable> tests = new Array<Drawable>();
    int MAX_IDX = files.length;
    int idx = 0;
    float transitionTime = 5;
    long timer;

    public ChapterOneIntroduction(GameScreen s) {
        super(s.getGame());
        for (String str: files) {
            tests.add(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("backgrounds/"+str)))));
        }
        setBackground(tests.get(idx));
        Label label = new Label("PRESS ESC TO EXIT", AssetsLoader.uiSkin);
        label.setColor(Color.BLACK);
        label.setFontScale(2);
        add(label).expand().bottom();

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (nanoTime()-timer >= (transitionTime*4)*1e9) {
            timer = nanoTime();
            idx = (idx+1)%MAX_IDX;
            setBackground(tests.get(idx));
        }
    }

    @Override
    public void show() {
        timer = nanoTime();
        clearActions();
        addAction(color(Color.WHITE, 0));
        addAction(forever(sequence(color(Color.ORANGE, transitionTime),
                color(new Color(0.8f, 0, 0.2f, 1), transitionTime), color(Color.NAVY, transitionTime),
                color(Color.WHITE, transitionTime))));
    }
}
