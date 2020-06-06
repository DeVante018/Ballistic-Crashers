package hello

import java.io.FileInputStream

import javafx.scene.image.{Image, ImageView}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.paint.Color._
import scalafx.scene.{Group, Scene}


object ScalaFXHelloWorld extends JFXApp {

  val inputStream: FileInputStream = new FileInputStream("/Users/DeVante/Desktop/SummerGameProject/src/Balistic_Crashers/Assets/Ships/testImage.jpg")
  val image: Image = new Image(inputStream)
  val imageView: ImageView = new ImageView(image)
  imageView.setX(200)
  imageView.setY(0)
  imageView.setPreserveRatio(true)
  var sceneGraphics: Group = new Group {}
  sceneGraphics.children.add(imageView)

  stage = new PrimaryStage() {
    title = "Balistic Crashers"
    scene = new Scene(1000, 1000) {
      fill = Black
      content = List(sceneGraphics)
    }
  }
}