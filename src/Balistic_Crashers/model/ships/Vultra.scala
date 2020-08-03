package Balistic_Crashers.model.ships

import java.io.FileInputStream

import Balistic_Crashers.model.ships.Trait.Ship
import javafx.scene.image.{Image, ImageView}

class Vultra(x: Double, y: Double) extends Ship {

  val inputStream: FileInputStream = new FileInputStream("/Users/DeVante/Desktop/SummerGameProject/src/Balistic_Crashers/Assets/Ships/Ship #1_TEST.png")
  val image: Image = new Image(inputStream)
  val lapixShipImage: ImageView = new ImageView(image)
  lapixShipImage.setX(200)
  lapixShipImage.setY(0)
  lapixShipImage.setFitHeight(80)
  lapixShipImage.setFitWidth(100)

  override val stationaryAttackBonus: Double = 15.50

  var spd: Double = 10.0

  var atk: Double = 10.0

  override def speed(s: Double): Unit = {
    spd += s
  }

  override def attack(a: Double): Unit = {
    atk += a
  }

  override def special(): Unit = {}

  override def secret(): Unit = {}

  override def getShip(): ImageView = lapixShipImage

}
