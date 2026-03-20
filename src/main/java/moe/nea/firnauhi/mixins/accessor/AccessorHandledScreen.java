package moe.nea.firnauhi.mixins.accessor;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractContainerScreen.class)
public interface AccessorHandledScreen {
    @Accessor("hoveredSlot")
    @Nullable
	Slot getFocusedSlot_Firnauhi();

    @Accessor("imageWidth")
    int getBackgroundWidth_Firnauhi();

    @Accessor("imageWidth")
    void setBackgroundWidth_Firnauhi(int newBackgroundWidth);

    @Accessor("imageHeight")
    int getBackgroundHeight_Firnauhi();

    @Accessor("imageHeight")
    void setBackgroundHeight_Firnauhi(int newBackgroundHeight);

    @Accessor("leftPos")
    int getX_Firnauhi();

    @Accessor("leftPos")
    void setX_Firnauhi(int newX);

    @Accessor("topPos")
    int getY_Firnauhi();

    @Accessor("topPos")
    void setY_Firnauhi(int newY);

}
