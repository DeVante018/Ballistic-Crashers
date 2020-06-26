package Balistic_Crashers.ArtificialIntelligence

import Balistic_Crashers.enemies.Enemies
import javafx.scene.image.ImageView

trait AI {
  def doEnemyMovement(enemy:(ImageView,Enemies),deltaTime:Double):Unit
  var enemyName:String
  var enemyMovementInterval:Double
  var typeName:String
  var scaleFactor:Double = 0.1
}
