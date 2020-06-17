package Balistic_Crashers.model.Ships

import java.io.FileInputStream

import Balistic_Crashers.model.Ships.Trait.Ship
import javafx.scene.image.{Image, ImageView}

class Lapix(x: Double, y: Double) extends Ship {
  val inputStream: FileInputStream = new FileInputStream("/Users/DeVante/Desktop/SummerGameProject/src/Balistic_Crashers/Assets/Ships/Ship #1_TESTa1.png")
  val image: Image = new Image(inputStream)
  val lapixShipImage: ImageView = new ImageView(image)

  def Lapix(){
    lapixShipImage.setX(x)
    lapixShipImage.setY(y)
    lapixShipImage.setFitHeight(175)
    lapixShipImage.setFitWidth(175)
    lapixShipImage.setPreserveRatio(true)
  }
  Lapix()

  override val stationaryAttackBonus: Double = 15.50

  var spd: Double = 85.00

  var atk: Double = 12.0

  override def getShip(): ImageView = {
    lapixShipImage
  }

  override def speed(s: Double): Unit = {
    spd += s
  }

  override def attack(a: Double): Unit = {
    atk += a
  }

  override def special(): Unit = {
    // some sort of special. be creative
  }

  override def secret(): Unit = {
    spd *= 2
    atk *= 2
  }

}
