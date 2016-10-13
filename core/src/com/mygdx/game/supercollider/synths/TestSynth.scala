package com.mygdx.game.supercollider.synths

import de.sciss.synth._
import ugen._
import Ops._

/**
  * Created by afr on 13.10.16.
  */
object TestSynth {
  val df = SynthDef("AnalogBubbles" ) {
    val f1 = "freq1".kr(0.4)
    val f2 = "freq2".kr(8)
    val d  = "detune".kr(0.90375)
    val f  = LFSaw.ar(f1).madd(24, LFSaw.ar(Seq(f2, f2 * d)).madd(3, 80)).midicps // glissando function
    val x  = CombN.ar(SinOsc.ar(f) * 0.04, 0.2, 0.2, 4) // echoing sine wave
    Out.ar(0, x)

  }
  def apply():de.sciss.synth.Synth = {df.play()}
}
