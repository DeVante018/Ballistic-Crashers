package Balistic_Crashers.ArtificialIntelligence

import Balistic_Crashers.enemies.Enemies
import scalafx.scene.shape.Shape

trait AI {
  def doEnemyMovement(enemy:(Shape,Enemies),deltaTime:Double):Unit
  var enemyName:String
  var enemyMovementInterval:Double
  var typeName:String
  var scaleFactor:Double = 0.1
}
