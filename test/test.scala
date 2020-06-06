import Balistic_Crashers.Player
import Balistic_Crashers.controller.WASDInputs
import Balistic_Crashers.model.Game
import org.scalatest.{BeforeAndAfter, FlatSpec}

class test extends FlatSpec with BeforeAndAfter {

  val player_1: Player = new Player(20.00, 30.00, "lapix")
  val gameTestObj: Game = new Game
  val inputs: WASDInputs = new WASDInputs(player_1)

  behavior of "key input"
  it should "correctly update player location with game " in {
  }

}
