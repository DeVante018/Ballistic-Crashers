package Balistic_Crashers.model.Obsticles

import Balistic_Crashers.model.Coordinates.Location

class obsticle(loc: Location) {

  private var spin: Double = 0.0
  private var length: Double = 0.0
  private var width: Double = 0.0

  def setSpin(set: Double): Unit = {
    spin = set
  }

  def setLength(set: Double): Unit = {
    length = set
  }

  def setWidth(set: Double): Unit = {
    width = set
  }
}
