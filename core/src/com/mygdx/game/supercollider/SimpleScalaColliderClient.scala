package com.mygdx.game.supercollider

import de.sciss.synth._
import com.mygdx.game.supercollider.synths.TestSynth

/**
  * Created by afr on 27.09.16.
  */
class SimpleScalaColliderClient extends SupercolliderClient {

  // variables holding the server representation and connection
  private var server:Server = null
  private var serverConnection:ServerConnection = Server.boot()(
    {case ServerConnection.Running(srv) => {new AnyRef}.synchronized {server = srv}}
  )

  override def playTest(): Unit = {
    TestSynth()
  }

  override def close(): Unit ={
    if (server != null) {
      if (server.condition != Server.Offline) server.quit()
    } else serverConnection.abort()
  }
}