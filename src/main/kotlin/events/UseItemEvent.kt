package moe.nea.firnauhi.events

import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.InteractionHand
import net.minecraft.world.level.Level

data class UseItemEvent(val playerEntity: Player, val world: Level, val hand: InteractionHand) : FirnauhiEvent.Cancellable() {
	companion object : FirnauhiEventBus<UseItemEvent>()
	val item: ItemStack = playerEntity.getItemInHand(hand)
}
