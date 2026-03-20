
package moe.nea.firnauhi.util.customgui

import net.minecraft.world.inventory.Slot

interface CoordRememberingSlot {
    fun rememberCoords_firnauhi()
    fun restoreCoords_firnauhi()
    fun getOriginalX_firnauhi(): Int
    fun getOriginalY_firnauhi(): Int
}

val Slot.originalX get() = (this as CoordRememberingSlot).getOriginalX_firnauhi()
val Slot.originalY get() = (this as CoordRememberingSlot).getOriginalY_firnauhi()
