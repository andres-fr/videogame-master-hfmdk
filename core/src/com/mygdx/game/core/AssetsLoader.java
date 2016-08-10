package com.mygdx.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by afr on 08.08.16.
 */

public class AssetsLoader {
    public static Skin uiSkin;

    public static void load() {

        uiSkin = new Skin(Gdx.files.internal("uiskin/uiskin.json"));


        //atlas = new TextureAtlas(Gdx.files.internal("skins/uiskin.atlas"));
        //skin = new Skin();
        //skin.addRegions(atlas);
    }

/*    private static void makeDrawableButtonStyle() {
        // Generate a 1x1 white texture and store it in the skin named "white".
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));
        skin.add("default", new BitmapFont());
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
    }*/

    public static void dispose() {
        uiSkin.dispose();
        //atlas.dispose();
    }
}
