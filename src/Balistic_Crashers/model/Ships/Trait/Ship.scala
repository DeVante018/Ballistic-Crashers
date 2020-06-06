package Balistic_Crashers.model.Ships.Trait

import javafx.scene.image.ImageView

trait Ship {
  val stationaryAttackBonus: Double

  var spd: Double

  var atk: Double

  def speed(s: Double): Unit

  def attack(a: Double): Unit

  def special(): Unit

  def secret(): Unit

  def getShip(): ImageView
}
