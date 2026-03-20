
package moe.nea.firnauhi.util.customgui

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen

@Suppress("FunctionName")
interface HasCustomGui {
    fun getCustomGui_Firnauhi(): CustomGui?
    fun setCustomGui_Firnauhi(gui: CustomGui?)
}

var <T : AbstractContainerScreen<*>> T.customGui: CustomGui?
    get() = (this as HasCustomGui).getCustomGui_Firnauhi()
    set(value) {
        (this as HasCustomGui).setCustomGui_Firnauhi(value)
    }

