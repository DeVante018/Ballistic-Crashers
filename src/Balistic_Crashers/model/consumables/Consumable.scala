package Balistic_Crashers.model.consumables

import Balistic_Crashers.Player

trait Consumable {
  def use(player:Player):Unit
}
