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
    private String currentAtlasName;
    private ArrayMap<String, Skin> skins = new ArrayMap<String, Skin>();
    private ArrayMap<String, TextureAtlas> atlases = new ArrayMap<String, TextureAtlas>();
    private Array<TextureAtlas.AtlasRegion> regions;


    public AssetsManager() {
    }

    private void loadAtlas(String name, String localAddress) {
        if (atlases.get(name) == null) {
            atlases.put(name, new TextureAtlas(Gdx.files.internal(localAddress)));
        }
    }

    private void deleteAtlas(String atlasName) {
        TextureAtlas ta = atlases.get(atlasName);
        atlases.removeKey(atlasName);
        ta.dispose();
        if (currentAtlasName == atlasName) {
            currentAtlasName = null;
            regions = null;
        }
    }

    private void setCurrentAtlas(String name) {
        currentAtlasName = name;
        regions = atlases.get(name).getRegions();
    }

    public TextureAtlas getAtlas(String str) {
        return atlases.get(currentAtlasName);
    }

    public TextureAtlas getCurrentAtlas() {
        return atlases.get(currentAtlasName);
    }

    public TextureAtlas.AtlasRegion getRegion(int idx) {
        return regions.get(idx);
    }

    public TextureAtlas.AtlasRegion getRegion(String regionName) {
        return getCurrentAtlas().findRegion(regionName);
    }

    public TextureRegionDrawable getRegionDrawable(String regionName) {
        return new TextureRegionDrawable(getCurrentAtlas().findRegion(regionName));
    }

    public String getCurrentAtlasName() {
        return currentAtlasName;
    }

    private void loadSkin(String name, String localAddress) {
        if (skins.get(name) == null) {
            skins.put(name, new Skin(Gdx.files.internal("uiskin/uiskin.json")));
        }
    }
    private void deleteSkin(String skinName) {
        Skin s = skins.get(skinName);
        skins.removeKey(skinName);
        s.dispose();
    }

    public Skin getSkin(String skinName) {
        return skins.get(skinName);
    }


    public void prepareInit() {
        //loadSkin("uiskin", "uiskin/uiskin.json");
        loadAtlas("menuscene", "menuscene-packed/pack.atlas");
        setCurrentAtlas("menuscene");
    }

    public void prepareScene1() {
        loadAtlas("scene1", "scene1-packed/pack.atlas");
        setCurrentAtlas("scene1");
    }


    public void dispose() {
        for (Skin s : skins.values()) s.dispose();
        for (TextureAtlas ta : atlases.values()) ta.dispose();
    }



}
