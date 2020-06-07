package Balistic_Crashers.model

import Balistic_Crashers.ArtificialIntelligence.UpDown
import Balistic_Crashers.Player
import Balistic_Crashers.enemies.{Enemies, Sputter}
import Balistic_Crashers.gameplay.Script
import Balistic_Crashers.model.World.Nexus
import Balistic_Crashers.model.World.`trait`.levelTrait
import scalafx.scene.Group
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Rectangle, Shape}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
class Game {

  var playerAttackLasersMap: mutable.Map[Shape, Attacks] = mutable.Map() // maps all lasers shot by the player
  var enemiesAttackLazersMap: mutable.Map[Shape, Enemies] = mutable.Map() // maps all lasers shot by the enemies
  var script:ArrayBuffer[Script] = new ArrayBuffer[Script]()
  var enemiesMap: mutable.Map[Shape, Enemies] = mutable.Map()
  var world: levelTrait = generateLevel("Nexus")
  val player_1: Player = new Player(200.0, 300.0, "Lapix")

  createScript()

  def generateLevel(world: String): levelTrait = world.toLowerCase match {
    case "nexus" => new Nexus
    case "kinetica" => null
    case _ => null
  }

  val sceneGraphics: Group = new Group {}

  def update(deltaTime: Double): Unit = {
    //increase the total update time for the player
    player_1.lazerUpdateTimeThreashold += deltaTime
    val lazerCheck: Boolean = player_1.update(deltaTime)//checks if player is holding down the space button (shoot laser button)
    if (lazerCheck){
      if (player_1.lazerUpdateTimeThreashold > 0.2) { //if the set amount of time has passed then allow laser to fire
        createNewPlayerLazer()
        player_1.lazerUpdateTimeThreashold = 0.0
      }
    }
    //enemies attack timer
    for(enemies <- enemiesMap){
      enemies._2.updateLaserThreshold += deltaTime
    }
    for(enemies <- enemiesMap){
      if(enemies._2.updateLaserThreshold > enemies._2.alpha){
        createNewEnemyLazer(enemies._2)
        enemies._2.updateLaserThreshold = 0
      }
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
    enemiesAttackLazersMap += (newLazerShape -> enemy)
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

  def updateEnemyLaserPosition(enmLzrMap: mutable.Map[Shape, Enemies]): Unit = {
    for (theLazer <- enmLzrMap) {
      if (theLazer._1.translateX.toDouble - (theLazer._2.laserSpeed * .1) < -10) {
        enemiesAttackLazersMap -= theLazer._1
        sceneGraphics.children.remove(theLazer._1)
      }
      else {
        theLazer._1.translateX = theLazer._1.translateX.value - (theLazer._2.laserSpeed * .1)
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
        player_1.health -= laser._2.atk
        if (player_1.health <= 0) {
          println("player is dead")
        }
      }
    }
  }

  def generateEnemy(x:Double,y:Double,enemyType:String): Unit = {
    val create = new Rectangle {
      width = 60
      height = 40
      translateX = 0.0
      translateY = 0.0
      fill = Color.Purple
    }
    create.translateX = x
    create.translateY = y
    if(enemyType.toLowerCase() == "sputter"){
      enemiesMap += (create -> new Sputter(x,y))
    }
    sceneGraphics.children.add(create)
  }
  def createScript():Unit = {
    var scr:Script = new Script(12,new UpDown("sputter",20)) // AI object

    script += scr
  }
  private var scriptPos:Int = 0
  private var nextEvent:Boolean = true
  private var scriptTimer:Double = 0.0

  def runScript(dt:Double):Unit = {
    val randomSpawn = util.Random
    var enemyPos:Int = 0
    var currentEvent:Script = new Script(0, new UpDown("sputter",20))
    if(nextEvent && scriptPos < script.size){
      currentEvent = script(scriptPos)
      for(_ <- 1 to currentEvent.enmCnt){
        enemyPos = randomSpawn.nextInt(1200)
        if(enemyPos < 400)enemyPos = 400
        generateEnemy(enemyPos,randomSpawn.nextInt(600),currentEvent.behavior.enemyName)
      }
      nextEvent = false
    }
    if(scriptTimer < currentEvent.behavior.time){
      scriptTimer += dt
      if(scriptTimer >= currentEvent.behavior.time){
        scriptTimer = currentEvent.behavior.time
      }
    }
    if(enemiesMap.isEmpty){
      if(scriptTimer != currentEvent.behavior.time){
        scriptTimer = currentEvent.behavior.time - 1
      }
    }
    if(scriptTimer == currentEvent.behavior.time){
      if(enemiesMap.isEmpty){
        nextEvent = true
        scriptPos += 1
      }
    }
  }
}
