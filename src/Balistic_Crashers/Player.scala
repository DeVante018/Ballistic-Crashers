package Balistic_Crashers

import Balistic_Crashers.model.Ships.Trait.Ship
import Balistic_Crashers.model.Ships.{Drogon, Lapix, StarScream, Vultra}
import Balistic_Crashers.model.coordinates.Location
import Balistic_Crashers.statepatterns.PlayerState
import Balistic_Crashers.statepatterns.playstates.Still
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Rectangle, Shape}

class Player(locX: Double, locY: Double, shipName: String) {

  //create player location
  val playerLocation: Location = new Location(locX, locY)
  //create players ship
  val ship: Ship = generateShip(shipName, playerLocation.locx, playerLocation.locy)
  //player health
  var health: Double = 100
  //speed scale factor
  val scaleFactor: Double = 0.1
  //time for player to update
  var lazerUpdateTimeThreashold:Double = 0.0
  // set different states for the player
  var stateOfPlayer: PlayerState = new Still(this)
  var count: Int = 0

  //boolean state variables
  var goLeftHeld: Boolean = false
  var goRightHeld: Boolean = false
  var goUpHeld: Boolean = false
  var goDownHeld: Boolean = false
  var spaceHeld: Boolean = false

  // Start API press methods
  def leftPressed(): Unit = {
    this.goLeftHeld = true
  }

  def rightPressed(): Unit = {
    this.goRightHeld = true
  }

  def upPressed(): Unit = {
    this.goUpHeld = true
  }

  def downPressed(): Unit = {
    this.goDownHeld = true
  }

  def spacePressed(): Unit = {
    this.spaceHeld = true
  }

  // start API release methods
  def leftReleased(): Unit = {
    this.goLeftHeld = false
    this.stateOfPlayer.leftReleased()
  }

  def rightReleased(): Unit = {
    this.goRightHeld = false
    this.stateOfPlayer.rightReleased()
  }

  def upReleased(): Unit = {
    this.goUpHeld = false
    this.stateOfPlayer.upReleased()
    count = 0
  }

  def downReleased(): Unit = {
    this.goDownHeld = false
    this.stateOfPlayer.downReleased()
  }

  def spaceReleased(): Unit = {
    this.spaceHeld = false
  }

  def movingRight(): Unit = {
    //set boundaries
    count += 1
    if (playerLocation.locx + (ship.spd * scaleFactor) >= 1185) {
      playerLocation.locx = 1185
      ship.getShip().setX(playerLocation.locx)
    }
    else {
      playerLocation.locx += (ship.spd * scaleFactor)
      ship.getShip().setX(playerLocation.locx)
    }
  }

  def movingLeft(): Unit = {
    // set boundaries
    if (playerLocation.locx - (ship.spd * scaleFactor) <= 0) {
      playerLocation.locx = 0
      ship.getShip().setX(playerLocation.locx)
    }
    else {
      playerLocation.locx -= (ship.spd * scaleFactor)
      ship.getShip().setX(playerLocation.locx)
    }
  }

  def movingUp(): Unit = {
    // set boundaries
    if (playerLocation.locy - (ship.spd * scaleFactor) <= -25) {
      playerLocation.locy = -25
      ship.getShip().setY(playerLocation.locy)
    }
    else {
      playerLocation.locy += (-ship.spd * scaleFactor)
      ship.getShip().setY(playerLocation.locy)
    }
  }

  def movingDown(): Unit = {
    //set boundaries
    if (playerLocation.locy + (ship.spd * scaleFactor) >= 680) {
      playerLocation.locy = 680
      ship.getShip().setY(playerLocation.locy)
    }
    else {
      playerLocation.locy += (ship.spd * scaleFactor)
      ship.getShip().setY(playerLocation.locy)
    }
  }

  def update(dt: Double): Boolean = {
    this.stateOfPlayer.update(dt)
  }

  def generateShip(str: String, x: Double, y: Double): Ship = str.toLowerCase match {
    case "lapix" => new Lapix(x, y)
    case "vultra" => new Vultra(x, y)
    case "starscream" => new StarScream(x, y)
    case "quickstar" => new Drogon(x, y)
    case _ => null
  }

  def generateLazer(player: Player): Shape = {
    new Rectangle() {
      width = 7
      height = 3
      translateX = player.playerLocation.locx
      translateY = player.playerLocation.locy
      fill = Color.Red
    }
  }
}
