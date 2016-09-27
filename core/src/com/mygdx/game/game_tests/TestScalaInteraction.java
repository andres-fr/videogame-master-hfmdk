package com.mygdx.game.game_tests;


import com.mygdx.game.scalacollider.ScalaClassForTestingPurposes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by afr on 27.09.16.
 */

public class TestScalaInteraction {
    JavaClassForTestingPurposes j = new JavaClassForTestingPurposes();
    ScalaClassForTestingPurposes s = new ScalaClassForTestingPurposes();

    /**
     * this method tests if it is possible for a scala class (must be in the
     * scalla collider folder) to instantiate a java class and call its method
     */
    @Test
    public void testScalaJava_1() {
        assertTrue(s.instantiateJavaTestClassAndGetString()==j.getTestString());
    }

    /**
     * this method tests if it is possible for a java class (must be in the
     * scalla collider folder) to instantiate a scala class and get a field from it
     */
    @Test
    public void testScalaJava_2() {
        assertTrue(j.instantiateScalaClassAndGetString() == s.TEST_STRING());
    }

    /**
     * check scalaclass methods
     */
    @Test
    public void testScalaJava_3() {
        Number n = 1234.5678;
        assertTrue(n.equals(s.returnDasNumber()));
    }

    /**
     * check scalaclass methods
     */
    @Test
    public void testScalaJava_4() {
        Number n = 4321;
        assertTrue(n.equals(s.returnIasNumber()));
    }

    /**
     * check scalaclass methods
     */
    @Test
    public void testScalaJava_5() {
        assertTrue(1234.5678 == s.returnDasDouble());
    }

    /**
     * check scalaclass methods
     */
    @Test
    public void testScalaJava_6() {
        assertTrue(4321 == s.returnIasInteger());
    }
}
