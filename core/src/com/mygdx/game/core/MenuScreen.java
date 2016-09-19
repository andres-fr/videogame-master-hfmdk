package com.mygdx.game.core;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.MyGame;

/**
 * Created by afr on 19.09.16.
 */

public class MenuScreen extends GameScreenUI {
    // size config
    public static int PREF_MENU_WIDTH = (int)(MyGame.WIDTH*0.9);
    public static int PREF_MENU_HEIGHT = (int)(MyGame.HEIGHT*0.9);
    protected Table clusterTable = new Table();

    public MenuScreen(MyGame g) {
        super(g);
        clusterTable.setSize(PREF_MENU_WIDTH, PREF_MENU_HEIGHT);
        clusterTable.setPosition((MyGame.WIDTH-PREF_MENU_WIDTH)/2, (MyGame.HEIGHT-PREF_MENU_HEIGHT)/2);
        clusterTable.defaults().center();
        stage.addActor(clusterTable);
    }
}
