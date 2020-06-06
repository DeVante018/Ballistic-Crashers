package Balistic_Crashers.view

import Balistic_Crashers.controller.WASDInputs
import Balistic_Crashers.model.Game
import javafx.event.ActionEvent
import javafx.scene.control.Button
import javafx.scene.input.KeyEvent
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.effect.DropShadow
import scalafx.scene.paint.Color._
import scalafx.scene.paint.{Color, LinearGradient, Stops}
import scalafx.scene.shape.{Rectangle, Shape}
import scalafx.scene.text.Text
import scalafx.scene.{Group, Scene}


object GUI extends JFXApp {

  var sceneGraphics: Group = new Group {}

  val titleBalistic: Text = new Text(275.00, 200.00, "Balistic") {
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

  val titleCrashers: Text = new Text(500.00, 200.00, "Crashers") {
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

  val startButton: javafx.scene.control.Button = new Button("Start") {
    color(0.5, 0.8, 0.4)
    setStyle("-fx-font-size: 29pt")
    setLayoutX(435)
    setLayoutY(250)
    setOnAction((event: ActionEvent) => {
      startGame()
    })
  }
  sceneGraphics.children.add(titleBalistic)
  sceneGraphics.children.add(titleCrashers)
  sceneGraphics.children.add(startButton)

  stage = new PrimaryStage() {
    title = "Balistic Crashers"
    scene = new Scene(1080, 1000) {
      fill = Black
      content = List(sceneGraphics)
    }
  }

  def playerSprite(xLocation: Double, yLocation: Double, color: Color): Shape = {
    new Rectangle {
      width = 89
      height = 78
      translateX = 10
      translateY = 10
      fill = color
    }
  }

  // start of the game menu
  def startGame(): Unit = {

    var lastUpdateTime: Long = System.nanoTime()
    val game: Game = new Game()

    game.sceneGraphics.children.add(game.player_1.ship.getShip())
    stage = new PrimaryStage() {
      title = "Nexus"
      scene = new Scene(1341, 750) {
        fill = DarkGray
        content = List(game.sceneGraphics)
        addEventHandler(KeyEvent.ANY, new WASDInputs(game.player_1))
      }
      val update: Long => Unit = (time: Long) => {
        val dt: Double = (time - lastUpdateTime) / 1000000000.0
        lastUpdateTime = time
        game.update(dt)
        game.generateEnemy(dt)
      }
      AnimationTimer(update).start()
    }
  }
}
