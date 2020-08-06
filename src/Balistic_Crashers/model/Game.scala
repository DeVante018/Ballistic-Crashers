package Balistic_Crashers.model

import java.io.FileInputStream

import Balistic_Crashers.Player
import Balistic_Crashers.artificialIntelligence.{AI, UpDown}
import Balistic_Crashers.enemies.{Enemies, Sputter}
import Balistic_Crashers.gameplay.Script
import Balistic_Crashers.model.consumables.{Consumable, Health, LaserBuff}
import Balistic_Crashers.model.coordinates.Location
import Balistic_Crashers.model.world.Nexus
import Balistic_Crashers.model.world.`trait`.levelTrait
import javafx.geometry.Rectangle2D
import javafx.scene.image.{Image, ImageView}
import scalafx.scene.Group
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.LightBlue
import scalafx.scene.shape.{Rectangle, Shape}
import scalafx.scene.text.Text

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
class Game {


  var playerAttackLasersMap: mutable.Map[Shape, Attacks] = mutable.Map() // maps all lasers shot by the player
  var enemiesAttackLazersMap: mutable.Map[Shape, Enemies] = mutable.Map() // maps all lasers shot by the enemies
  var enemiesMap: mutable.Map[ImageView, Enemies] = mutable.Map()
  var consumableArray: mutable.ArrayBuffer[String] = mutable.ArrayBuffer()
  var script:ArrayBuffer[Script] = new ArrayBuffer[Script]()

  val player_1: Player = new Player(200.0, 300.0, "Lapix")
  var world: levelTrait = generateLevel("Nexus")
  var playerHealthBar: Shape = healthBar(0.0)
  val textForHealth: Text = healthText()
  val background: ImageView = generateBackground()
  var generateConsumableTimer: Double = 0.0
  var currentConsumable: Consumable = new Health(new Location(0,0))//needs a temporary place holder item
  val sceneGraphics: Group = new Group {}
  var itemDelay:Boolean = false

  sceneGraphics.children.add(background)
  sceneGraphics.children.add(textForHealth)
  sceneGraphics.children.add(playerHealthBar)
  sceneGraphics.children.add(player_1.ship.getShip())


  initializeConsumablesArray()
  createScript()
  def generateLevel(world: String): levelTrait = world.toLowerCase match {
    case "nexus" => new Nexus
    case "kinetica" => null
    case _ => null
  }


  def update(deltaTime: Double): Unit = {
    player_1.lazerUpdateTimeThreashold += deltaTime
    generateConsumableTimer += deltaTime

    /** player attack laser interval */
    val lazerCheck: Boolean = player_1.update(deltaTime)//checks if player is holding down the space button (shoot laser button)
    if (lazerCheck){
      if (player_1.lazerUpdateTimeThreashold > 0.3) { //if the set amount of time has passed then allow laser to fire
        createNewPlayerLazer()
        player_1.lazerUpdateTimeThreashold = 0.0
      }
    }

    /** enemy attack timer intervals */
    for(enemies <- enemiesMap){
      enemies._2.laserUpdateTimeAccumulator += deltaTime // interval between when enemies fire
    }
    for(enemies <- enemiesMap){
      if(enemies._2.animationDone) {
        if (enemies._2.laserUpdateTimeAccumulator > enemies._2.laserUpdateAlpha) {
          createNewEnemyLazer(enemies._2)
          enemies._2.laserUpdateTimeAccumulator = 0
        }
      }
    }

    /** consumable generation method */
    if(currentConsumable.notOnScreen){//defaults for boolean is true
      if(!itemDelay){
        if (generateConsumableTimer > 10.0) {
          generateConsumable()
          generateConsumableTimer = 0.0
          currentConsumable.notOnScreen = false
        }
      }
      else{
        currentConsumable.timer += deltaTime
        if(currentConsumable.timer > currentConsumable.timerAlpha){
          itemDelay = false
          currentConsumable.timer = 0
        }
      }
    }
    else{
      generateConsumableTimer = 0.0
      currentConsumable.moveImage()
      if(detectCollisionConsumable()){
        sceneGraphics.children.remove(currentConsumable.itemImage)
        currentConsumable.notOnScreen = true
        if(currentConsumable.name == "laser"){
          itemDelay = true
        }
      }
      if(currentConsumable.itemImage.getX < -25.0){
        currentConsumable.notOnScreen = true
        currentConsumable.itemImage.setX(1500.00)
        sceneGraphics.children.remove(currentConsumable.itemImage)
      }
      detectCollisionConsumable()
    }

    /** update game methods */
    updateEnemyLaserPosition(enemiesAttackLazersMap)
    updatePlayerLaserPosition(playerAttackLasersMap)
    scrollScreen()
    checkEnemyHit()
    checkPlayerHit()
  }

  def createNewPlayerLazer(): Unit = {
    val newLazerShape = new Rectangle {
      width = 12
      height = 3
      translateX = player_1.playerLocation.locx + 170
      translateY = player_1.playerLocation.locy + 83
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
      translateX = enemy.loc.locx - 17
      translateY = enemy.loc.locy + 60
      fill = Color.Green
    }
    enemiesAttackLazersMap += (newLazerShape -> enemy)
    sceneGraphics.children.add(newLazerShape)
  }

  def updatePlayerLaserPosition(shapeToAttacks: mutable.Map[Shape, Attacks]): Unit = {
    for (theLazer <- shapeToAttacks) {
      if (theLazer._1.translateX.toDouble + (140 * .1) > 1500) {
        playerAttackLasersMap -= theLazer._1
        sceneGraphics.children.remove(theLazer._1)
      }
      else {
        theLazer._1.translateX = theLazer._1.translateX.toDouble + (140 * .1)
      }
      //println(playerAttackLasersMap.size)
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
    }
  }

  def checkEnemyHit(): Unit = {
    for (laser <- playerAttackLasersMap) {
      for (enemyShip <- enemiesMap) {
        if(enemyShip._2.name == "sputter"){
          sputterHitBox(enemyShip,laser)
        }
      }
    }
  }

  def checkPlayerHit(): Unit = {
    var enemyDamageAmount:Double = 0.0
    for (laser <- enemiesAttackLazersMap){
      val deltaDistanceX: Double = laser._1.getTranslateX - player_1.ship.getShip().getX //give some leverage on whats a hit
      val deltaDistanceY: Double = laser._1.getTranslateY - player_1.ship.getShip().getY //give some leverage on whats a hit
      if ((deltaDistanceX <= 135 && deltaDistanceX >= 0) && (deltaDistanceY <= 88 && deltaDistanceY >= 31)){
        enemyDamageAmount += laser._2.atk
        enemiesAttackLazersMap -= laser._1
        sceneGraphics.children.remove(laser._1)
        player_1.health -= laser._2.atk
        if (player_1.health <= 0) {
          println("player is dead")
        }
      }
    }
    if(enemyDamageAmount > 0.0){
      sceneGraphics.children.remove(playerHealthBar)
      playerHealthBar = healthBar(enemyDamageAmount)
      sceneGraphics.children.add(playerHealthBar)
    }
    if(player_1.health < 30.0){
      playerHealthBar.fill = Color.Red
    }
    else{ playerHealthBar.fill = Color.Green}
  }

  def generateEnemy(x:Double,y:Double,enemyType:String,intelType:AI): Enemies = {
    var newEnemy = new Sputter(x,y,intelType.typeName)

    if(enemyType.toLowerCase() == "sputter"){
      newEnemy = new Sputter(x,y,intelType.typeName)
      enemiesMap += (newEnemy.enemyShipImage -> newEnemy)
    }
    sceneGraphics.children.add(newEnemy.enemyShipImage)
    newEnemy
  }

  //this method is how to create a script for the game. If interested check out the read-me file to learn how to make it yourself
  def createScript():Unit = {
    script += new Script(7,new UpDown("sputter",3.0))
    script += new Script(5,new UpDown("sputter",2.0))
    script += new Script(3,new UpDown("sputter",1.0))
    script += new Script(15,new UpDown("sputter",3.0))
  }


  private var scriptPos:Int = 0
  private var nextEvent:Boolean = true
  private var spawnDelay:Double = 0.0
  private var enemyShipZoomIn:Double = 10.0

  def runScript(dt:Double):Unit = {
    spawnDelay += dt
    val randomSpawn = util.Random
    var enemyPos:Int = 0
    val currentEvent:Script = script(scriptPos)
    if(nextEvent){
      for(_ <- 1 to currentEvent.enmCnt){
        enemyPos = randomSpawn.nextInt(1200)
        if(enemyPos < 400)enemyPos = 400
        val en = generateEnemy(1400,randomSpawn.nextInt(600),currentEvent.behavior.enemyName,currentEvent.behavior)
        en.stopAnimationXpos = enemyPos
      }
      nextEvent = false
    }
    if(enemiesMap.isEmpty && scriptPos < script.size - 1){
      nextEvent = true
      scriptPos += 1
    }
    if(enemiesMap.nonEmpty){
      enemyMovement(dt,currentEvent)
    }
  }

  def enemyMovement(dt:Double,curEvent:Script): Unit = {
    var bool:Boolean = true
    for(enemy <- enemiesMap){
      if(enemy._2.animationDone){
        enemy._2.timer += dt
        curEvent.behavior.doEnemyMovement(enemy, enemy._2.timer)
      }
      else{
        if(bool){
          shipEntryAnimation(enemy)
          bool = false
        }
      }
    }
  }

  def shipEntryAnimation(tuple: (ImageView, Enemies)): Unit = {
    if(tuple._2.loc.locx - enemyShipZoomIn <= tuple._2.stopAnimationXpos){
      tuple._2.loc.locx = tuple._2.stopAnimationXpos
      tuple._1.setX(tuple._2.stopAnimationXpos)
    }
    else {
      tuple._2.loc.locx -= enemyShipZoomIn
      tuple._1.setX(tuple._2.loc.locx)
      enemyShipZoomIn -= 0.04

    }
    if(tuple._2.loc.locx == tuple._2.stopAnimationXpos){
      tuple._2.animationDone = true
      enemyShipZoomIn = 10.0
    }
  }

  def healthText(): Text =  {
    new Text(20, 740, "Health:") {
      style = "-fx-font-size: 15pt"
      fill = LightBlue
    }
  }

  def healthBar(amount:Double): Shape =  {
    var healthBarWidth:Double = (2 * player_1.health) - (2 * amount)
    if(healthBarWidth <= 5.0 && player_1.health > 0.0){
      healthBarWidth = 5.0
    }
    new Rectangle {
      width = healthBarWidth
      height = 5
      translateX = 89
      translateY = 735
      fill = Color.Green
    }
  }

  def generateBackground():ImageView = {
    val inputStreamBack: FileInputStream = new FileInputStream("/Users/DeVante/Desktop/SummerGameProject/src/Balistic_Crashers/assets/scenery/Background.png")
    val imageBack: Image = new Image(inputStreamBack)
    val back: ImageView = new ImageView(imageBack)
    val viewport: Rectangle2D = new Rectangle2D(0,0,1400,775)
    back.setViewport(viewport)
    back
  }
  def sputterHitBox(enemyData:(ImageView,Enemies),laser:(Shape,Attacks)):Unit = {
    val deltaDistanceX: Double = laser._1.getTranslateX - enemyData._1.getX //give some leverage on whats a hit
    val deltaDistanceY: Double = enemyData._1.getY - laser._1.getTranslateY //give some leverage on whats a hit
    if ((deltaDistanceY >= -40 && deltaDistanceY <= -10) && (deltaDistanceX >= 40 && deltaDistanceX < 114)) { //hit box for top orb of UFO
      playerAttackLasersMap -= laser._1 // laser remove
      sceneGraphics.children.remove(laser._1)
      val okayToGetHit:Double = enemyData._2.loc.locx - enemyData._2.stopAnimationXpos
      if(enemyData._2.animationDone || okayToGetHit <= 200.00 ){
        enemyData._2.health -= player_1.ship.atk
      }
      if(enemyData._2.health <= 0) {
        sceneGraphics.children.remove(enemyData._1)
        enemiesMap -= enemyData._1
        enemyData._2.animationDone = true
        enemyShipZoomIn = 10.00
      }
    }
    else if((deltaDistanceY >= -90 && deltaDistanceY <= -40) && (deltaDistanceX >= 0 && deltaDistanceX < 166)) { //hit box for top orb of UFO
      playerAttackLasersMap -= laser._1 // laser remove
      sceneGraphics.children.remove(laser._1)
      val okayToGetHit:Double = enemyData._2.loc.locx - enemyData._2.stopAnimationXpos
      if(enemyData._2.animationDone || okayToGetHit <= 200.00 ){
        enemyData._2.health -= player_1.ship.atk
      }
      if(enemyData._2.health <= 0) {
        sceneGraphics.children.remove(enemyData._1)
        enemiesMap -= enemyData._1
        enemyData._2.animationDone = true
        enemyShipZoomIn = 10.00
      }
    }
  }

  def initializeConsumablesArray(): Unit =  {
    val randomNumber = util.Random
    for (x <- 1 to 5) { //25% chance receiving health or laser buff
      consumableArray += "health"
      consumableArray += "laser"
    }
    for ( x <- 1 to 10){ // 50% chance of receiving score buff
      //consumableArray += "score"
    }
  }

  def generateConsumable(): Unit = {
    val randomNumber = util.Random
    val consumableName = consumableArray(randomNumber.nextInt(9))
    if(consumableName == "health"){
      currentConsumable = new Health(new Location(1500,randomNumber.nextInt(700)))
    }
    else if(consumableName == "laser"){
      currentConsumable = new LaserBuff(new Location(1500,randomNumber.nextInt(700)))
    }
    else{
      currentConsumable = new Health(new Location(1500,randomNumber.nextInt(700)))
    }
    sceneGraphics.children.add(currentConsumable.itemImage)
  }

  def scrollScreen():Unit = {
    val view = background.getViewport
    var xVal = view.getMinX
    if(xVal >= 2732){
      xVal = 0
    }
    val viewport: Rectangle2D = new Rectangle2D(xVal + 1.0,0,1400,775)
    background.setViewport(viewport)
  }

  def detectCollisionConsumable():Boolean = {
    false
  }
}
