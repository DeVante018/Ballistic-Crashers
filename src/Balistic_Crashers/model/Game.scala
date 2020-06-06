package Balistic_Crashers.model

import Balistic_Crashers.Enemies.{Enemies, Sputter}
import Balistic_Crashers.Player
import Balistic_Crashers.model.World.Nexus
import Balistic_Crashers.model.World.`trait`.levelTrait
import scalafx.scene.Group
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Rectangle, Shape}

import scala.collection.mutable

class Game {
  var enemyLazerUpdateThreashold: Double = 0.0
  var enemyUpdate: Double = 0.0
  var playerAttackLasersMap: mutable.Map[Shape, Attacks] = mutable.Map()
  var enemiesAttackLazersMap: mutable.Map[Shape, Double] = mutable.Map()

  var enemiesMap: mutable.Map[Shape, Enemies] = mutable.Map()
  var world: levelTrait = generateLevel("Nexus")
  val player_1: Player = new Player(200.0, 300.0, "Lapix")

  def generateLevel(world: String): levelTrait = world.toLowerCase match {
    case "nexus" => new Nexus
    case "kinetica" => null
    case _ => null
  }

  val sceneGraphics: Group = new Group {}

  def update(deltaTime: Double): Unit = {
    player_1.lazerUpdateTimeThreashold += deltaTime
    enemyLazerUpdateThreashold += deltaTime
    val lazerCheck: Boolean = player_1.update(deltaTime)
    if (lazerCheck) {
      if (player_1.lazerUpdateTimeThreashold > 0.2) {
        createNewPlayerLazer()
        player_1.lazerUpdateTimeThreashold = 0.0
      }
    }
    //enemies attack timer
    if (enemyLazerUpdateThreashold > 0.4) {
      for (enemies <- enemiesMap) {
        createNewEnemyLazer(enemies._2)
      }
      enemyLazerUpdateThreashold = 0
    }
    updateEnemyLaserPosition(enemiesAttackLazersMap)
    updatePlayerLaserPosition(playerAttackLasersMap)
    checkEnemyHit()
    checkPlayerHit()
  }

  def createNewPlayerLazer(): Unit = {
    val newLazerShape = new Rectangle {
      width = 12
      height = 3
      translateX = player_1.playerLocation.locx + 170
      translateY = player_1.playerLocation.locy + 53
      fill = Color.Red
    }
    val newLazerAttributes = new Attacks(player_1)
    playerAttackLasersMap += (newLazerShape -> newLazerAttributes)
    sceneGraphics.children.add(newLazerShape)
  }

  def createNewEnemyLazer(enemy: Enemies): Unit = {
    val newLazerShape = new Rectangle {
      width = 12
      height = 3
      translateX = enemy.loc.locx
      translateY = enemy.loc.locy
      fill = Color.Green
    }
    enemiesAttackLazersMap += (newLazerShape -> enemy.atk)
    sceneGraphics.children.add(newLazerShape)
  }

  def updatePlayerLaserPosition(shapeToAttacks: mutable.Map[Shape, Attacks]): Unit = {
    for (theLazer <- shapeToAttacks) {
      if (theLazer._1.translateX.toDouble + (120 * .1) > 1400) {
        playerAttackLasersMap -= theLazer._1
        sceneGraphics.children.remove(theLazer._1)
      }
      else {
        theLazer._1.translateX = theLazer._1.translateX.toDouble + (120 * .1)
      }
      println(playerAttackLasersMap.size)
    }
  }

  def updateEnemyLaserPosition(enmLzrMap: mutable.Map[Shape, Double]): Unit = {
    for (theLazer <- enmLzrMap) {
      if (theLazer._1.translateX.toDouble - (100 * .1) < -10) {
        enemiesAttackLazersMap -= theLazer._1
        sceneGraphics.children.remove(theLazer._1)
      }
      else {
        theLazer._1.translateX = theLazer._1.translateX.value - (100 * .1)
      }
      //println(playerAttackLasersMap.size)
    }
  }

  def checkEnemyHit(): Unit = {
    for (laser <- playerAttackLasersMap) {
      for (enemyShip <- enemiesMap) {
        val deltaDistanceX: Double = laser._1.getTranslateX - enemyShip._1.getTranslateX //give some leverage on whats a hit
        val deltaDistanceY: Double = enemyShip._1.getTranslateY - laser._1.getTranslateY //give some leverage on whats a hit
        if ((deltaDistanceY >= -40 && deltaDistanceY <= 1) && (deltaDistanceX <= 60 && deltaDistanceX > 0)) {
          playerAttackLasersMap -= laser._1 // laser remove
          sceneGraphics.children.remove(laser._1)
          enemyShip._2.health -= player_1.ship.atk
          println("player hit")
          if (enemyShip._2.health <= 0) {
            sceneGraphics.children.remove(enemyShip._1)
            enemiesMap -= enemyShip._1
          }
        }
      }
    }
  }

  def checkPlayerHit(): Unit = {
    for (laser <- enemiesAttackLazersMap) {
      val deltaDistanceX: Double = laser._1.getTranslateX - player_1.ship.getShip().getX //give some leverage on whats a hit
      val deltaDistanceY: Double = laser._1.getTranslateY - player_1.ship.getShip().getY //give some leverage on whats a hit
      if ((deltaDistanceX <= 135 && deltaDistanceX >= 0) && (deltaDistanceY <= 45 && deltaDistanceY >= 3)) {
        enemiesAttackLazersMap -= laser._1
        sceneGraphics.children.remove(laser._1)
        player_1.health -= laser._2
        if (player_1.health <= 0) {
          println("player is dead")
        }
      }
    }
  }

  def generateEnemy(dt: Double): Unit = {
    enemyUpdate += dt
    if (enemyUpdate >= 5 && enemiesMap.isEmpty) {
      val create = new Rectangle {
        width = 60
        height = 40
        translateX = 500
        translateY = 500
        fill = Color.Purple
      }
      sceneGraphics.children.add(create)
      enemiesMap += (create -> new Sputter(500, 500))
      enemyUpdate = 0
    }
  }
}
