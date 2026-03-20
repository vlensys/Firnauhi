package moe.nea.firnauhi.mixins.custommodels;

import moe.nea.firnauhi.features.texturepack.HeadModelChooser;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStackRenderState.class)
public class ItemRenderStateExtraInfo implements HeadModelChooser.HasExplicitHeadModelMarker {
	@Unique
	boolean hasExplicitHead_firnauhi = false;

	@Inject(method = "clear", at = @At("HEAD"))
	private void clear(CallbackInfo ci) {
		hasExplicitHead_firnauhi = false;
	}

	@Override
	public void markExplicitHead_Firnauhi() {
		hasExplicitHead_firnauhi = true;
	}

	@Override
	public boolean isExplicitHeadModel_Firnauhi() {
		return hasExplicitHead_firnauhi;
	}
}
