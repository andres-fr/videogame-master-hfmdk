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
    public enum PREPARE {PERMANENT, LOBBY, CHAPTER1}
    private PREPARE currentAtlasLabel = null;
    private TextureAtlas currentAtlas = null;


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

    public PREPARE getCurrentAtlasLabel() {
        return currentAtlasLabel;
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

    public void dispose() {
        skin.dispose();
        permanentAtlas.dispose();
        currentAtlas.dispose();
    }

    /**
     * @param atlasLabel    the PREPARE label to design the atlas to be loaded
     * @param localAddress the string where to find the atlas in the assets (f.e. "atlases/myatlas")
     */
    private void setCurrentAtlas(PREPARE atlasLabel, String localAddress) {
        if (!atlasLabel.equals(currentAtlasLabel)){
            if (MyGame.DEBUG) System.out.println("AssetsManager: preparing "+atlasLabel+"...");
            if (currentAtlas != null) currentAtlas.dispose();
            currentAtlas = new TextureAtlas(Gdx.files.internal(localAddress));
            currentAtlasLabel = atlasLabel;
        }
    }

    public void prepare(PREPARE p) {
        switch (p) {
            case PERMANENT:
                // do nothing
                break;
            case LOBBY:
                setCurrentAtlas(p, "atlases/lobby.atlas");
                break;
            case CHAPTER1:
                setCurrentAtlas(p, "atlases/chapter1.atlas");
                break;
        }
    }
}
