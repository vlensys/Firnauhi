package moe.nea.firnauhi.mixins.render.entitytints;

import moe.nea.firnauhi.events.EntityRenderTintEvent;
import moe.nea.firnauhi.util.render.TintedOverlayTexture;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityRenderState.class)
public class EntityRenderStateTint implements EntityRenderTintEvent.HasTintRenderState {
	@Unique
	int tint = -1;
	@Unique
	TintedOverlayTexture overlayTexture;
	@Unique
	boolean hasTintOverride = false;

	@Override
	public int getTint_firnauhi() {
		return tint;
	}

	@Override
	public void setTint_firnauhi(int i) {
		tint = i;
		hasTintOverride = true;
	}

	@Override
	public boolean getHasTintOverride_firnauhi() {
		return hasTintOverride;
	}

	@Override
	public void setHasTintOverride_firnauhi(boolean b) {
		hasTintOverride = b;
	}

	@Override
	public void reset_firnauhi() {
		hasTintOverride = false;
		overlayTexture = null;
	}

	@Override
	public @Nullable TintedOverlayTexture getOverlayTexture_firnauhi() {
		return overlayTexture;
	}

	@Override
	public void setOverlayTexture_firnauhi(@Nullable TintedOverlayTexture tintedOverlayTexture) {
		this.overlayTexture = tintedOverlayTexture;
	}
}
