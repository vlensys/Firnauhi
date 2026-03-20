
package moe.nea.firnauhi.mixins.customgui;

import moe.nea.firnauhi.util.customgui.CoordRememberingSlot;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Slot.class)
public class OriginalSlotCoords implements CoordRememberingSlot {

    @Shadow
    public int x;
    @Shadow
    public int y;
    @Unique
    public int originalX;
    @Unique
    public int originalY;

    @Override
    public void rememberCoords_firnauhi() {
        this.originalX = this.x;
        this.originalY = this.y;
    }

    @Override
    public void restoreCoords_firnauhi() {
        this.x = this.originalX;
        this.y = this.originalY;
    }

    @Override
    public int getOriginalX_firnauhi() {
        return originalX;
    }

    @Override
    public int getOriginalY_firnauhi() {
        return originalY;
    }
}
