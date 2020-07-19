package Balistic_Crashers.enemies

import Balistic_Crashers.model.coordinates.Location
/** Each enemy will have these traits and the update timer for all enemies will be tracked within the enemy itself
 * This will allow for more individual behavior by each enemy ship */
trait Enemies {
  val name:String
  var timer: Double = 0.0 //timer for enemy movement
  val loc: Location // location for each enemy
  var speed: Double // movement speed
  var health: Double// health amount
  var atk: Double // damage value
  var laserSpeed:Double// speed laser travels
  var laserUpdateTimeAccumulator:Double = 0.0 // time accumulator ** this will be compared to laserUpdateAlpha **
  var laserUpdateAlpha:Double // interval between when lasers fire
  var animationDone:Boolean = false // spawn in animation is finished
  var stopAnimationXpos:Double // location when the spawn in animation should stop
  def initializeShip():Unit
}
