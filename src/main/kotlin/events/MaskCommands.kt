

package moe.nea.firnauhi.events

import com.mojang.brigadier.CommandDispatcher

data class MaskCommands(val dispatcher: CommandDispatcher<*>) : FirnauhiEvent() {
    companion object : FirnauhiEventBus<MaskCommands>()

    fun mask(name: String) {
        dispatcher.root.children.removeIf { it.name.equals(name, ignoreCase = true) }
    }
}
