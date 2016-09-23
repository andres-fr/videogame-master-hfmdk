package com.mygdx.game.screens.chapter1;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGame;
import com.mygdx.game.actors.Door;
import com.mygdx.game.core.AssetsManager;
import com.mygdx.game.core.GameScreenGAMEPLAY;


/**
 * Created by afr on 19.09.16.
 */

public class RoomChapter1Screen extends GameScreenGAMEPLAY {

    public RoomChapter1Screen(MyGame g) {
        super(g, AssetsManager.PREPARE.CHAPTER1, 0.9f, 0.2f, 0.75, "room05");
        addWalkzoneScaled(new int[]{0, 0, 100000000, 0 , 100000000, 100000000, 0, 100000000}); // testing wz
        // add door to street
        int doorXpos = 700;
        int doorYpos = 190;
        Door streetDoor = new Door(game, doorXpos, doorYpos, 90, 265, StreetChapter1Screen.class);
        stage.addActor(streetDoor);
        // add and configure Player initial pos
        stage.addActor(game.player);
        game.player.setPosition(doorXpos, doorYpos);
    }
}
