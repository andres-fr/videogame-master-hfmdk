package com.mygdx.game.screens.chapter1;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.MyGame;
import com.mygdx.game.actors.Door;
import com.mygdx.game.core.AssetsManager;
import com.mygdx.game.core.GameScreenGAMEPLAY;

/**
 * Created by afr on 15.09.16.
 */

public class StreetChapter1Screen extends GameScreenGAMEPLAY {

    public StreetChapter1Screen(MyGame g) {
        super(g, AssetsManager.PREPARE.CHAPTER1,  0.9f, 0.2f, 0.75, "street_bg", "street_shadows", "street_lights");
        float cycleTime = 6f;
        background.addAction(Actions.forever(Actions.sequence(Actions.color(Color.WHITE, cycleTime), Actions.color(Color.NAVY, cycleTime))));
        lights.addAction(Actions.forever(Actions.sequence(Actions.fadeOut(cycleTime), Actions.fadeIn(cycleTime))));
        shadows.addAction(Actions.forever(Actions.sequence(Actions.fadeOut(cycleTime), Actions.fadeIn(cycleTime))));
        addWalkzoneScaled(new int[]{0, 0, 6697, 0, 6696, 21, 3887, 641, 3733, 645, 3731, 320, 1935, 352, 1917, 321, 1829, 320, 1823, 362, 1445, 369,
                1186, 335, 1139, 369, 1146, 453, 1028, 465, 966, 443, 903, 443, 825, 464, 788, 495, 736, 491, 757, 337, 306, 238, 72, 249, 0, 270, 0, 1035});
        // add apartment portal
        int doorXpos = 1460;
        int doorYpos = 240;
        Door apartmentPortal = new Door(game, doorXpos, doorYpos, 100, 220, RoomChapter1Screen.class);
        stage.addActor(apartmentPortal);

        // add and configure Player initial pos
        stage.addActor(game.player);
        game.player.setPosition(doorXpos, doorYpos);


        //!!!!!!!!!!!!!!
        stage.addAction(Actions.run(new Runnable() {
            @Override
            public void run() {
                //game.getScClient().playTest();
                game.getScClient().playTest();
            }
        }));
    }
}
