package Balistic_Crashers.Enemies

import Balistic_Crashers.model.Coordinates.Location

class Sputter(x: Double, y: Double) extends Enemies {
  override var speed: Double = 15.00
  override var health: Double = 100.00
  override val loc: Location = new Location(x, y)
  override var atk: Double = 9.0
  override var alpha: Double = 1.95
  override var laserSpeed: Double = 80.34
}
