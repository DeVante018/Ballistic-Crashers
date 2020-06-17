package Balistic_Crashers.enemies

import Balistic_Crashers.model.Coordinates.Location

class Sputter(x: Double, y: Double,movement:String) extends Enemies {
  override var speed: Double = 15.00
  override var health: Double = 30.00
  override val loc: Location = new Location(x, y)
  override var atk: Double = 9.0
  override var LaserUpdateAlpha: Double = 1.95
  override var laserSpeed: Double = 80.34
  override var stopAnimationXpos: Double = 0.0
}
