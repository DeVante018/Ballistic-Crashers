package Balistic_Crashers.Enemies

import Balistic_Crashers.model.Coordinates.Location

trait Enemies {
  val loc: Location
  var speed: Double
  var health: Double
  var atk: Double
  var dt: Double
}
