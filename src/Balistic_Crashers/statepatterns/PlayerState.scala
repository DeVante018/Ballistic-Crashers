package Balistic_Crashers.statepatterns

import Balistic_Crashers.Player

abstract class PlayerState(player: Player) {

  var timeInState: Double = 0.0

  def update(dt: Double): Boolean = {
    timeInState += dt
    if (player.goLeftHeld && player.goRightHeld) {
      //do nothing because why are you doing this you weirdo?
    }
    else {
      if (player.goLeftHeld) {
        this.leftKeyPressed()
      }
      if (player.goRightHeld) {
        this.rightKeyPressed()
      }
    }
    if (player.goDownHeld) {
      this.dowmKeyPressed()
    }
    if (player.goUpHeld) {
      this.upKeyPressed()
    }
    if (player.spaceHeld) {
      return true
    }
    false
  }


  // key press API
  def leftKeyPressed(): Unit = {}

  def rightKeyPressed(): Unit = {}

  def upKeyPressed(): Unit = {}

  def dowmKeyPressed(): Unit = {}

  def spaceKeyPressed(): Unit = {}

  // key release API
  def leftReleased(): Unit = {}

  def rightReleased(): Unit = {}

  def downReleased(): Unit = {}

  def upReleased(): Unit = {}

  def isAlive: Boolean = {
    true
  }

}
