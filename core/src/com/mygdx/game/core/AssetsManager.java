package com.mygdx.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.game.MyGame;

/**
 * Created by afr on 26.08.16.
 */

public class AssetsManager {
    // skin containers
    private Skin skin = new Skin(Gdx.files.internal("uiskin/uiskin.json"));
    // atlas containers
    private TextureAtlas permanentAtlas;
    private String currentAtlasName = null;
    private TextureAtlas currentAtlas = null;
    public enum PREPARE {LOBBY, CHAPTER1}


    public AssetsManager() {
        permanentAtlas = new TextureAtlas(Gdx.files.internal("atlases/permanent.atlas"));
    }

    public Skin getSkin() {
        return skin;
    }

    public TextureAtlas getPermanentAtlas() {
        return permanentAtlas;
    }

    public TextureAtlas getCurrentAtlas() {
        return currentAtlas;
    }

    public String getCurrentAtlasName() {
        return currentAtlasName;
    }

    public TextureAtlas.AtlasRegion getPermanentRegion(int idx) {
        return permanentAtlas.getRegions().get(idx);
    }

    public TextureAtlas.AtlasRegion getPermanentRegion(String regName) {
        return permanentAtlas.findRegion(regName);
    }

    public TextureAtlas.AtlasRegion getCurrentRegion(int idx) {
        return currentAtlas.getRegions().get(idx);
    }

    public TextureAtlas.AtlasRegion getCurrentRegion(String regName) {
        return currentAtlas.findRegion(regName);
    }

    /*
    public TextureRegionDrawable getRegionDrawable(String regionName) {
        return new TextureRegionDrawable(getCurrentAtlas().findRegion(regionName));
    }
    */


    public void dispose() {
        skin.dispose();
        permanentAtlas.dispose();
        currentAtlas.dispose();
    }

    /**
     * @param atlasName    just for debugging purposes, give a name to the atlas
     * @param localAddress the string where to find the atlas in the assets (f.e. "atlases/myatlas")
     */
    private void setCurrentAtlas(String atlasName, String localAddress) {
        if (!atlasName.equals(currentAtlasName)){
            if (currentAtlas != null) currentAtlas.dispose();
            currentAtlas = new TextureAtlas(Gdx.files.internal(localAddress));
            currentAtlasName = atlasName;
        }
    }

    public void prepare(PREPARE p) {
        if (p == PREPARE.LOBBY){
            if (MyGame.DEBUG) System.out.println("preparing lobby...");
            setCurrentAtlas("lobby", "atlases/lobby.atlas");
        }
        else if (p == PREPARE.CHAPTER1){
            if (MyGame.DEBUG) System.out.println("preparing chapter1...");
            setCurrentAtlas("chapter1", "atlases/chapter1.atlas");
        }
    }
}
