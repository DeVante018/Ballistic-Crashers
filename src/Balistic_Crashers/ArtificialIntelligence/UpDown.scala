package Balistic_Crashers.ArtificialIntelligence

import Balistic_Crashers.enemies.Enemies
import scalafx.scene.shape.Shape

class UpDown(enemy:String,dt:Double) extends AI{

  override var time:Double = dt
  override var enemyName:String  = enemy
  override var typeName: String = "updown"

  override def moveEnemyAxisUp(enemy:(Shape,Enemies)): Unit = {
    enemy._1.translateY.value -= 1.0
    enemy._2.loc.locy -= 1
  }

  override def moveEnemyAxisDown(enemy: (Shape, Enemies)): Unit = {
    enemy._1.translateY.value += 1.0
    enemy._2.loc.locy += 1
  }

  override def moveEnemyAxisLeft(enemy:(Shape,Enemies)): Unit = {
    enemy._1.translateX.value += 1.0
  }

  override def moveEnemyAxisRight(enemy: (Shape, Enemies)): Unit = {}
}
