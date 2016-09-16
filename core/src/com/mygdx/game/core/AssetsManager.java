package com.mygdx.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

/**
 * Created by afr on 26.08.16.
 */

public class AssetsManager {
    private Skin skin = new Skin(Gdx.files.internal("uiskin/uiskin.json"));
    private TextureAtlas permanentAtlas;
    private String chapterAtlasName = null;
    private TextureAtlas chapterAtlas = null;


    public AssetsManager() {
        permanentAtlas = new TextureAtlas(Gdx.files.internal("atlases/permanent"));
    }

    public Skin getSkin() {
        return skin;
    }

    public TextureAtlas getChapterAtlas() {
        return chapterAtlas;
    }

    public String getChapterAtlasName() {
        return chapterAtlasName;
    }

    public TextureAtlas.AtlasRegion getPermanentRegion(int idx) {
        return permanentAtlas.getRegions().get(idx);
    }

    public TextureAtlas.AtlasRegion getPermanentRegion(String regName) {
        return permanentAtlas.findRegion(regName);
    }

    public TextureAtlas.AtlasRegion getChapterRegion(int idx) {
        return chapterAtlas.getRegions().get(idx);
    }

    public TextureAtlas.AtlasRegion getChapterRegion(String regName) {
        return chapterAtlas.findRegion(regName);
    }

    /*
    public TextureRegionDrawable getRegionDrawable(String regionName) {
        return new TextureRegionDrawable(getCurrentAtlas().findRegion(regionName));
    }
    */


    public void dispose() {
        skin.dispose();
        permanentAtlas.dispose();
        chapterAtlas.dispose();
    }

    /**
     * @param atlasName just for debugging purposes, give a name to the atlas
     * @param localAddress the string where to find the atlas in the assets (f.e. "atlases/myatlas")
     */
    private void loadChapterAtlas(String atlasName, String localAddress) {
        if (chapterAtlas != null) chapterAtlas.dispose();
        chapterAtlas = new TextureAtlas(Gdx.files.internal(localAddress));
        chapterAtlasName = atlasName;
    }

    public void loadPresentation(){ loadChapterAtlas("presentation", "atlases/presentation");}

    public void loadChapter1() {
        loadChapterAtlas("chapter1", "atlases/chapter1");
    }
}
