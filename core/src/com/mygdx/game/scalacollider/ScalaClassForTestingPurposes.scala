package com.mygdx.game.scalacollider

import com.mygdx.game.game_tests.JavaClassForTestingPurposes


/**
  * Created by afr on 27.09.16.
  */

/**
  * this class is intended to hold examples for all needed behaviours
  * in the java+scala interaction. It is used in the game_tests moduled
  */
class ScalaClassForTestingPurposes {

  val TEST_STRING = "THISISTHETESTSTRINGINTHESCALACLASS"

  /**
    * 1) This method instantiates a .java class, and calls a method in it
    */
  def instantiateJavaTestClassAndGetString(): String ={
    val c = new JavaClassForTestingPurposes()
    return c.getTestString
  }

  /**
    * This object holds two values and two methods returning them.
    * IT IS NOT POSSIBLE TO ACCESS ANY OF THEM FROM A JAVA CLASS:
    * ==> s.TestObject$.MODULE$.i() will say s should be changed with ScalaClass...
    * ==> when changed, it compiles OK but it fails to run, because of
    * "cannot find symbol MODULE$". So the scala class may have objects,
    * but must provide free functions to access them
    */
  object TestObject {
    val d = 1234.5678
    val i = 4321
    def returnDouble :Double = d
    def returnInteger :Int = i
  }

  def returnDasNumber: Number = TestObject.d
  def returnIasNumber: Number =TestObject.i
  def returnDasDouble: Double = return TestObject.returnDouble
  def returnIasInteger: Int = TestObject.returnInteger

}
