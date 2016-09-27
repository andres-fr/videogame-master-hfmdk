package com.mygdx.game.game_tests;

/**
 * Created by afr on 27.09.16.
 */

import com.mygdx.game.scalacollider.ScalaClassForTestingPurposes;

/**
 * This class is intended to hold any necessary field for testing purposes.
 * For example, when testing java+scala interaction, a java class is needed.
 */
public class JavaClassForTestingPurposes {
    private static final String TEST_STRING = "THISISTHETESTSTRINGINTHEJAVACLASS";

    public String getTestString() {
        return TEST_STRING;
    }

    /**
     * this method instantiates a scala class, and gets a field from it
     * @return
     */
    public String instantiateScalaClassAndGetString() {
        return new ScalaClassForTestingPurposes().TEST_STRING();
    }


}
