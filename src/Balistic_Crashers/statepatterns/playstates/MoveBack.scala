package Balistic_Crashers.statepatterns.playstates

import Balistic_Crashers.Player

class MoveBack(player: Player) extends motion(player) {

  override def leftKeyPressed(): Unit = {
    player.movingLeft()
  }

  override def rightKeyPressed(): Unit = {
    player.stateOfPlayer = new MoveForward(player)
    println("state move forward: MOVEBACK")
  }

  override def leftReleased(): Unit = {
    player.stateOfPlayer = new Still(player)
    println("state still: MOVEBACK")
  }
}
