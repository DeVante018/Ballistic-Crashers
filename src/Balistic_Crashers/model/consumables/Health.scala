package Balistic_Crashers.model.consumables

import java.io.FileInputStream

import Balistic_Crashers.Player
import Balistic_Crashers.model.coordinates.Location
import javafx.scene.image.{Image, ImageView}

class Health(loc:Location) extends Consumable {

  private val inputStream: FileInputStream = new FileInputStream("/Users/DeVante/Desktop/SummerGameProject/src/Balistic_Crashers/assets/items/HealthPack.png")
  private val image: Image = new Image(inputStream)
  override val itemImage: ImageView = new ImageView(image)
  override val timerAlpha: Double = 0.0
  override var timer: Double = 0.0
  override val name: String = "health"

  def use(player: Player): Unit = {
    if (player.health < 100 && player.health > 0) {
      player.health += 15
      if (player.health > 100) {
        player.health = 100
      }
    }
  }

  override def moveImage():Unit = {
    loc.locx = loc.locx-4.68
    itemImage.setX(loc.locx)
  }

  private def initializeLocation(): Unit = {
    itemImage.setX(loc.locx)
    itemImage.setY(loc.locy)
    itemImage.setFitHeight(50)
    itemImage.setFitWidth(50)
    itemImage.setPreserveRatio(true)
  }

  initializeLocation()
}
