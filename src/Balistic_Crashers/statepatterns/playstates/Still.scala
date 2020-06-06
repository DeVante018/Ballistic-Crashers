package Balistic_Crashers.statepatterns.playstates

import Balistic_Crashers.Player

class Still(player: Player) extends motion(player) {

  override def leftKeyPressed(): Unit = {
    player.stateOfPlayer = new MoveBack(player)
    player.movingLeft()
    println("state move back: STILL")
  }

  override def rightKeyPressed(): Unit = {
    player.stateOfPlayer = new MoveForward(player)
    player.movingRight()
    println("state move forward: STILL")
  }
}
