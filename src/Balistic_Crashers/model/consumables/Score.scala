package Balistic_Crashers.model.consumables
import java.io.FileInputStream

import Balistic_Crashers.model.coordinates.Location
import javafx.scene.image.{Image, ImageView}

class Score(loc:Location) extends Consumable {
  private val inputStream: FileInputStream = new FileInputStream("/Users/DeVante/Desktop/SummerGameProject/src/Balistic_Crashers/assets/items/HealthPack.png")
  private val image: Image = new Image(inputStream)
  override val itemImage: ImageView = new ImageView(image)

  override var timer: Double = 0.0
  override val name: String = "laser"

  override def moveImage(): Unit = {
    loc.locx = loc.locx-12.75
    itemImage.setX(loc.locx)
  }

  override val timerAlpha: Double = 0.0
}
