package Balistic_Crashers.Enemies

import Balistic_Crashers.model.Coordinates.Location
/** Each enemy will have these traits and the update timer for all enemies will be tracked within the enemy itself
 * This will allow for more individual behavior by each enemy ship */
trait Enemies {
  val loc: Location
  var speed: Double
  var health: Double
  var atk: Double
  var laserSpeed:Double
  var updateLazerThreashold:Double = 0.0
  var alpha:Double
}
