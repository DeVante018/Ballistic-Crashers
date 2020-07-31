package Balistic_Crashers.view

import java.io.FileInputStream

import Balistic_Crashers.controller.WASDInputs
import Balistic_Crashers.model.Game
import javafx.scene.image.{Image, ImageView}
import javafx.scene.input
import javafx.scene.input.KeyEvent
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.effect.DropShadow
import scalafx.scene.paint.Color._
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.Text
import scalafx.scene.{Group, Scene}


object GUI extends JFXApp {

  val game: Game = new Game()
  val sceneGraphics: Group = new Group {}

  val titleBalistic: Text = new Text(415.00, 200.00, "Ballistic") {
    style = "-fx-font-size: 48pt"
    fill = new LinearGradient(
      endX = 0,
      stops = Stops(SeaGreen, GreenYellow)
    )
    effect = new DropShadow {
      color = LawnGreen
      radius = 7
      spread = 0.25
    }
  }

  val titleCrashers: Text = new Text(660.00, 200.00, "Crashers") {
    style = "-fx-font-size: 48pt"
    fill = new LinearGradient(
      endX = 0,
      stops = Stops(Cyan, DodgerBlue)
    )
    effect = new DropShadow {
      color = DodgerBlue
      radius = 25
      spread = 0.25
    }
  }

  val inputStream: FileInputStream = new FileInputStream("/Users/DeVante/Desktop/SummerGameProject/src/Balistic_Crashers/assets/UI/Start_Button_Trans.png")
  val image: Image = new Image(inputStream)
  val startButtonOverlay: ImageView = new ImageView(image)
  startButtonOverlay.setX(580)
  startButtonOverlay.setY(240)
  startButtonOverlay.setPreserveRatio(true)
  startButtonOverlay.setOnMouseClicked((event: input.MouseEvent) => startGame())

  sceneGraphics.children.add(titleBalistic)
  sceneGraphics.children.add(titleCrashers)
  sceneGraphics.children.add(startButtonOverlay)

  val parentStage: PrimaryStage = new PrimaryStage() {
    title = "Balistic Crashers"
    scene = new Scene(1341, 750) {
      fill = Black
      content = List(sceneGraphics)
    }
  }
  stage = parentStage

  def menu():Unit = {
    sceneGraphics.children.remove(titleBalistic)
    sceneGraphics.children.remove(titleCrashers)
    sceneGraphics.children.remove(startButtonOverlay)

    val Arcade: Text = new Text(600.5, 375.0, "Arcade") {
      style = "-fx-font-size: 19pt"
      fill = new LinearGradient(
        endX = 0,
        stops = Stops(LightGray,White)
      )
    }

    val timedEvent: Text = new Text(560.5, 415.0, "Timed Event") {
      style = "-fx-font-size: 19pt"
      fill = DarkGrey
    }

    sceneGraphics.children.add(Arcade)
    sceneGraphics.children.add(timedEvent)
  }

  // start of the game
  def startGame(): Unit = {
    var lastUpdateTime: Long = System.nanoTime()
    val textForHealth = healthText()
    game.sceneGraphics.children.add(textForHealth)
    game.sceneGraphics.children.add(game.player_1.ship.getShip())


    val childStage: PrimaryStage = new PrimaryStage(){
      title = "Nexus"
      scene = new Scene(1341, 750){
        fill = Black
        content = List(game.sceneGraphics)
        addEventHandler(KeyEvent.ANY, new WASDInputs(game.player_1)) // controller for player
      }
      val update: Long => Unit = (time: Long) => {
        val dt: Double = (time - lastUpdateTime) / 1000000000.0
        lastUpdateTime = time
        game.update(dt)
        game.runScript(dt)
      }
      AnimationTimer(update).start()
    }
    stage = childStage
  }


  def healthText(): Text =  {
    new Text(20, 740, "Health:") {
      style = "-fx-font-size: 15pt"
      fill = LightBlue
    }
  }
}
