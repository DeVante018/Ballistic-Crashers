package Balistic_Crashers.controller

import Balistic_Crashers.Player
import javafx.event.EventHandler
import javafx.scene.input.KeyEvent

abstract class KeyboardInputs(player: Player) extends EventHandler[KeyEvent] {

  val LEFT: String
  val RIGHT: String
  val UP: String
  val DOWN: String
  val SPACE: String

  override def handle(event: KeyEvent): Unit = {
    val keyCode = event.getCode
    event.getEventType.getName match {
      case "KEY_RELEASED" => keyCode.getName match {
        case this.LEFT => player.leftReleased()
        case this.RIGHT => player.rightReleased()
        case this.UP => player.upReleased()
        case this.DOWN => player.downReleased()
        case this.SPACE => player.spaceReleased()
      }
      case "KEY_PRESSED" => keyCode.getName match {
        case this.LEFT => player.leftPressed()
        case this.RIGHT => player.rightPressed()
        case this.UP => player.upPressed()
        case this.DOWN => player.downPressed()
        case this.SPACE => player.spacePressed()
      }
      case _ =>
    }
  }
}

class WASDInputs(player: Player) extends KeyboardInputs(player) {
  override val LEFT: String = "A"
  override val RIGHT: String = "D"
  override val UP: String = "W"
  override val DOWN: String = "S"
  override val SPACE: String = "Space"
}