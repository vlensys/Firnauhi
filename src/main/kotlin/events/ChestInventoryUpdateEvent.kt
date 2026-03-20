package moe.nea.firnauhi.events

import net.minecraft.world.item.ItemStack
import moe.nea.firnauhi.util.MC

sealed class ChestInventoryUpdateEvent : FirnauhiEvent() {
	companion object : FirnauhiEventBus<ChestInventoryUpdateEvent>()
	data class Single(val slot: Int, val stack: ItemStack) : ChestInventoryUpdateEvent()
	data class Multi(val contents: List<ItemStack>) : ChestInventoryUpdateEvent()
	val inventory = MC.screen
}
