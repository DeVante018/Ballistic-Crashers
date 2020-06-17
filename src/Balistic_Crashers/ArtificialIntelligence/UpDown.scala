package Balistic_Crashers.ArtificialIntelligence

import Balistic_Crashers.enemies.Enemies
import scalafx.scene.shape.Shape

class UpDown(enemy:String,dt:Double) extends AI{

  override var enemyMovementInterval:Double = dt
  override var enemyName:String  = enemy
  override var typeName: String = "updown"

  override def doEnemyMovement(enemy: (Shape, Enemies), deltaTime:Double): Unit = {
    if(deltaTime < dt ){
      if (enemy._1.translateY.value <= 5) {
        enemy._1.translateY.value = 5
        enemy._2.loc.locy = 5
      }
      else {
        enemy._1.translateY.value -= 1.0
        enemy._2.loc.locy -= 1.0
      }
    }
    else if(deltaTime > dt && deltaTime < (2*dt)){
      if (enemy._1.translateY.value >= 745) {
        enemy._1.translateY.value = 745
        enemy._2.loc.locy = 745
      }
      else {
        enemy._1.translateY.value += 1.0
        enemy._2.loc.locy += 1.0
      }
    }
    else enemy._2.timer = 0.0
  }
}
