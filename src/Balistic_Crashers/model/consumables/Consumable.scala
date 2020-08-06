package Balistic_Crashers.model.consumables

import javafx.scene.image.ImageView


trait Consumable {
  val itemImage:ImageView
  var timer:Double
  val timerAlpha:Double
  var notInUse:Boolean = true
  var notOnScreen:Boolean = true
  val name:String

  def moveImage():Unit
}
