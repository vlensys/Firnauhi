

package moe.nea.firnauhi.events

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen

data class ScreenRenderPostEvent(
    val screen: Screen,
    val mouseX: Int,
    val mouseY: Int,
    val tickDelta: Float,
    val drawContext: GuiGraphics
) : FirnauhiEvent() {
    companion object : FirnauhiEventBus<ScreenRenderPostEvent>()
}
