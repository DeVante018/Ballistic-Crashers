package Balistic_Crashers.model.consumables
import Balistic_Crashers.model.coordinates.Location

class LaserBuff(loc:Location) extends Consumable {
  var consumableTimer:Double = 0.0
  var consumableTimerAlpha:Double = 12.0
}
