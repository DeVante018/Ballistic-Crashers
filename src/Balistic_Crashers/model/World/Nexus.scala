package Balistic_Crashers.model.World

import Balistic_Crashers.model.Coordinates.Location
import Balistic_Crashers.model.Obsticles.obsticle
import Balistic_Crashers.model.World.`trait`.levelTrait

class Nexus extends levelTrait {

  override def scrollSpeed(): Unit = {}

  override def obsticleOccurrence(): Unit = {}

  def generateObject(): obsticle = {
    new obsticle(new Location(0.0, 0.0))
  }

}
