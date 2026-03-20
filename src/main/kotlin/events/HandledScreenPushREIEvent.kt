

package moe.nea.firnauhi.events

import me.shedaniel.math.Rectangle
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen

data class HandledScreenPushREIEvent(
    val screen: AbstractContainerScreen<*>,
    val rectangles: MutableList<Rectangle> = mutableListOf()
) : FirnauhiEvent() {

    fun block(rectangle: Rectangle) {
        rectangles.add(rectangle)
    }

    companion object : FirnauhiEventBus<HandledScreenPushREIEvent>()
}
