package Balistic_Crashers.model.World.`trait`

import Balistic_Crashers.model.Obsticles.obsticle

trait levelTrait {

  def scrollSpeed(): Unit

  def obsticleOccurrence(): Unit

  def generateObject(): obsticle

}
