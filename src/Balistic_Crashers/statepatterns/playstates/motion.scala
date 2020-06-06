package Balistic_Crashers.statepatterns.playstates

import Balistic_Crashers.Player
import Balistic_Crashers.statepatterns.PlayerState

abstract class motion(player: Player) extends PlayerState(player) {

  override def leftKeyPressed(): Unit = {
    player.leftPressed()
    player.stateOfPlayer = new MoveBack(player)
  }

  override def rightKeyPressed(): Unit = {
    player.rightPressed()
    player.stateOfPlayer = new MoveForward(player)
  }

  override def dowmKeyPressed(): Unit = {
    player.movingDown()
  }

  override def upKeyPressed(): Unit = {
    player.movingUp()
  }

}
