package Balistic_Crashers.enemies

import Balistic_Crashers.model.Coordinates.Location

class Sputter(x: Double, y: Double,movement:String) extends Enemies {
  override var speed: Double = 15.25 // speed of enemies
  override var health: Double = 30.00 // health of enemies
  override val loc: Location = new Location(x, y) // location of enemies
  override var atk: Double = 9.0 // attack damage of enemies
  override var laserUpdateAlpha: Double = 1.95 // interval between lasers shooting
  override var laserSpeed: Double = 80.34 // speed at which the lasers travel
  /** this value may not be used for all AI types */
  override var stopAnimationXpos: Double = 0.0 //the location at which the spawn in animation should stop
}
