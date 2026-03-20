

package moe.nea.firnauhi.events

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.DeltaTracker
import net.minecraft.world.item.ItemStack

data class HotbarItemRenderEvent(
    val item: ItemStack,
    val context: GuiGraphics,
    val x: Int,
    val y: Int,
    val tickDelta: DeltaTracker,
) : FirnauhiEvent() {
    companion object : FirnauhiEventBus<HotbarItemRenderEvent>()
}
