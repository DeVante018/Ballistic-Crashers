package Balistic_Crashers.ArtificialIntelligence

import Balistic_Crashers.enemies.Enemies
import javafx.scene.image.ImageView

class UpDown(enemy:String,dt:Double) extends AI{

  override var enemyMovementInterval:Double = dt
  override var enemyName:String  = enemy
  override var typeName: String = "updown"

  override def doEnemyMovement(enemy:(ImageView, Enemies), deltaTime:Double): Unit = {
    if(deltaTime < dt ){
      if (enemy._2.loc.locy <= 8) {
        enemy._2.loc.locy = 8
        enemy._1.setY(enemy._2.loc.locy)
      }
      else {
        enemy._2.loc.locy -= enemy._2.speed * scaleFactor
        enemy._1.setY(enemy._2.loc.locy)
      }
    }
    else if(deltaTime > dt && deltaTime < (2*dt)){
      if (enemy._2.loc.locy >= 745) {
        enemy._2.loc.locy = 745
        enemy._1.setY(enemy._2.loc.locy)
      }
      else {
        enemy._2.loc.locy += enemy._2.speed * scaleFactor
        enemy._1.setY(enemy._2.loc.locy)
      }
    }
    else enemy._2.timer = 0.0
  }
}
