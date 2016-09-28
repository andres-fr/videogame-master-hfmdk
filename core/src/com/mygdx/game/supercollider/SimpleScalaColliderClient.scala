package com.mygdx.game.supercollider

import de.sciss.synth._
import ugen._
import Ops._


/**
  * Created by afr on 27.09.16.
  */
class SimpleScalaColliderClient extends SupercolliderClient {

  // config and boot server at construction
  val cfg = Server.Config()
  cfg.program = "/usr/bin/scsynth"
  var s :ServerConnection = Server.boot

  // define synth
  private val f = LFSaw.kr(0.4).madd(24, LFSaw.kr(Seq(8, 7.23)).madd(3, 80)).midicps
  val testSynth = CombN.ar(SinOsc.ar(f) * 0.04, 0.2, 0.2, 4)
  var testNode : Synth = null


  //init server


  /*

  def playTest(): Unit ={
    Server.run(cfg) { s =>
      // play is imported from package de.sciss.synth.
      // it provides a convenience method for wrapping
      // a synth graph function in an `Out` element
      // and playing it back.

      play {
        val f = LFSaw.kr(0.4).madd(24, LFSaw.kr(Seq(8, 7.23)).madd(3, 80)).midicps
        CombN.ar(SinOsc.ar(f) * 0.04, 0.2, 0.2, 4)
      }

    }
  }

  */

  override def playTest(): Unit = {
    testNode = play{testSynth}
  }

  override def close(): Unit ={
    testNode.free()
  }
}