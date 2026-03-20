
package moe.nea.firnauhi.util

import me.shedaniel.math.Color
import net.minecraft.world.item.ItemStack
import moe.nea.firnauhi.events.FirnauhiEvent
import moe.nea.firnauhi.events.FirnauhiEventBus

data class DurabilityBarEvent(
    val item: ItemStack,
) : FirnauhiEvent() {
    data class DurabilityBar(
        val color: Color,
        val percentage: Float,
    )

    var barOverride: DurabilityBar? = null

    companion object : FirnauhiEventBus<DurabilityBarEvent>()
}
