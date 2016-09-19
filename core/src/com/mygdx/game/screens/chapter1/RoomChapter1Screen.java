package com.mygdx.game.screens.chapter1;

import com.mygdx.game.MyGame;
import com.mygdx.game.core.GameScreenGAMEPLAY;


/**
 * Created by afr on 19.09.16.
 */

public class RoomChapter1Screen extends GameScreenGAMEPLAY {

    public RoomChapter1Screen(MyGame g) {
        super(g, 0.7f, 0.025f, "room05");
        addWalkzoneScaled(new int[]{0, 0, 1920, 0, 1920, 1080, 1080, 0});
    }
}
