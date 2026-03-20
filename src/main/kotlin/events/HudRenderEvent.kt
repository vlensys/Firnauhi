package moe.nea.firnauhi.events

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.DeltaTracker
import net.minecraft.world.level.GameType
import moe.nea.firnauhi.util.MC

/**
 * Called when hud elements should be rendered, before the screen, but after the world.
 */
data class HudRenderEvent(val context: GuiGraphics, val tickDelta: DeltaTracker) : FirnauhiEvent.Cancellable() {
	val isRenderingHud = !MC.options.hideGui
	val isRenderingCursor = MC.interactionManager?.playerMode != GameType.SPECTATOR && isRenderingHud

	init {
		if (!isRenderingHud)
			cancel()
	}

	companion object : FirnauhiEventBus<HudRenderEvent>()
}
