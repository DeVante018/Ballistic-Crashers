package Balistic_Crashers.statepatterns.playstates

import Balistic_Crashers.Player

class MoveForward(player: Player) extends motion(player) {

  override def leftKeyPressed(): Unit = {
    player.stateOfPlayer = new MoveBack(player)
    println("state move back: MOVEFORWARD")
  }

  override def rightKeyPressed(): Unit = {
    player.movingRight()
  }

  override def rightReleased(): Unit = {
    player.stateOfPlayer = new Still(player)
    println("state still: MOVEFORWARD")
  }


}
