package moe.nea.firnauhi.events

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen

data class HandledScreenClickEvent(
	val screen: AbstractContainerScreen<*>,
	val mouseX: Double, val mouseY: Double, val button: Int
) :
	FirnauhiEvent.Cancellable() {
	companion object : FirnauhiEventBus<HandledScreenClickEvent>()
}
