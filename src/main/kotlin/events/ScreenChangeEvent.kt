

package moe.nea.firnauhi.events

import net.minecraft.client.gui.screens.Screen

data class ScreenChangeEvent(val old: Screen?, val new: Screen?) : FirnauhiEvent.Cancellable() {
    var overrideScreen: Screen? = null
    companion object : FirnauhiEventBus<ScreenChangeEvent>()
}
