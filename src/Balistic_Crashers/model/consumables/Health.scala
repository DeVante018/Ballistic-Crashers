package Balistic_Crashers.model.consumables

import Balistic_Crashers.Player
import Balistic_Crashers.model.coordinates.Location

class Health(loc:Location) extends Consumable {

  def use(player:Player): Unit = {
    if(player.health < 100 && player.health > 0){
      player.health += 15
      if(player.health > 100){
        player.health = 100
      }
    }
  }

}
