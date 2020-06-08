package Balistic_Crashers.ArtificialIntelligence

import Balistic_Crashers.enemies.Enemies
import scalafx.scene.shape.Shape

trait AI {
  def moveEnemyAxisUp(enemy:(Shape,Enemies)):Unit
  def moveEnemyAxisDown(enemy:(Shape,Enemies)):Unit
  def moveEnemyAxisLeft(enemy:(Shape,Enemies)):Unit
  def moveEnemyAxisRight(enemy:(Shape,Enemies)):Unit
  var enemyName:String
  var time:Double
  var typeName:String
}
