package com.mygdx.game.scala
import java.util.Calendar
import java.text.SimpleDateFormat

import com.mygdx.game.game_tests.TestScalaInteraction

/**
  * Created by afr on 27.09.16.
  */


/**
  * TestScalaClass tsc = new TestScalaClass();
  * tsc.test3();
  */
class TestScalaClass {


  /**
    * This method instantiates a .java class, and calls a method in it
    */
  def test1(): Unit ={
    val t = new TestScalaInteraction
    t.printsomething()

  }

  def test3(): Unit ={
    println(HelloWorld.getCurrentDate)
  }


  object HelloWorld {
    def getCurrentDate :String = getCurrentDateTime("EEEE, MMMM d")
  }


  private def getCurrentDateTime(dateTimeFormat: String): String = {
    val dateFormat = new SimpleDateFormat(dateTimeFormat)
    val cal = Calendar.getInstance()
    dateFormat.format(cal.getTime())
  }
}
