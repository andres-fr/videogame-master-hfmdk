package com.mygdx.game.supercollider

import de.sciss.synth._
import ugen._
import Ops._


/**
  * Created by afr on 27.09.16.
  */
class SimpleScalaColliderClient {

  val cfg = Server.Config()
  cfg.program = "/usr/bin/scsynth"


  Server.run(cfg) { s =>
    // play is imported from package de.sciss.synth.
    // it provides a convenience method for wrapping
    // a synth graph function in an `Out` element
    // and playing it back.

    val f = LFSaw.kr(0.4).madd(24, LFSaw.kr(Seq(8, 7.23)).madd(3, 80)).midicps
    CombN.ar(SinOsc.ar(f) * 0.04, 0.2, 0.2, 4)

  }


  //init server
  //Server.run(cfg){ s=>
  //}

  def playTest(): Unit ={
    Server.run(cfg) { s =>
      // play is imported from package de.sciss.synth.
      // it provides a convenience method for wrapping
      // a synth graph function in an `Out` element
      // and playing it back.

      val f = LFSaw.kr(0.4).madd(24, LFSaw.kr(Seq(8, 7.23)).madd(3, 80)).midicps
      CombN.ar(SinOsc.ar(f) * 0.04, 0.2, 0.2, 4)

    }
  }
}