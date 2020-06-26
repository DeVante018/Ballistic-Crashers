package Balistic_Crashers.enemies

import java.io.FileInputStream

import Balistic_Crashers.model.Coordinates.Location
import javafx.scene.image.{Image, ImageView}

class Sputter(x: Double, y: Double,movement:String) extends Enemies {
  override var speed: Double = 15.25 // speed of enemies
  override var health: Double = 30.00 // health of enemies
  override val loc: Location = new Location(x, y) // location of enemies
  override var atk: Double = 9.0 // attack damage of enemies
  override var laserUpdateAlpha: Double = 1.95 // interval between lasers shooting
  override var laserSpeed: Double = 80.34 // speed at which the lasers travel
  /** this value may not be used for all AI types */
  override var stopAnimationXpos: Double = 0.0 //the location at which the spawn in animation should stop
  private val inputStream: FileInputStream = new FileInputStream("/Users/DeVante/Desktop/SummerGameProject/src/Balistic_Crashers/assets/EnemyShips/sputter.png")
  private val image: Image = new Image(inputStream)
  val enemyShipImage: ImageView = new ImageView(image)
  override def initializeShip(): Unit = {
    enemyShipImage.setX(x)
    enemyShipImage.setY(y)
    enemyShipImage.setFitWidth(175)
    enemyShipImage.setFitHeight(175)
    enemyShipImage.setPreserveRatio(true)
  }
  initializeShip()
}
