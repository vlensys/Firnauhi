

package moe.nea.firnauhi.util.accessors

import me.shedaniel.math.Rectangle
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import moe.nea.firnauhi.mixins.accessor.AccessorHandledScreen

fun AbstractContainerScreen<*>.getProperRectangle(): Rectangle {
    this.castAccessor()
    return Rectangle(
        getX_Firnauhi(),
        getY_Firnauhi(),
        getBackgroundWidth_Firnauhi(),
        getBackgroundHeight_Firnauhi()
    )
}
