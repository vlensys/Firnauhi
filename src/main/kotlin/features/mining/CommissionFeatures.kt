package moe.nea.firnauhi.features.mining

import moe.nea.firnauhi.Firnauhi
import moe.nea.firnauhi.annotations.Subscribe
import moe.nea.firnauhi.events.SlotRenderEvents
import moe.nea.firnauhi.util.MC
import moe.nea.firnauhi.util.data.Config
import moe.nea.firnauhi.util.data.ManagedConfig
import moe.nea.firnauhi.util.mc.loreAccordingToNbt
import moe.nea.firnauhi.util.unformattedString

object CommissionFeatures {
	@Config
	object TConfig : ManagedConfig("commissions", Category.MINING) {
		val highlightCompletedCommissions by toggle("highlight-completed") { true }
	}


	@Subscribe
	fun onSlotRender(event: SlotRenderEvents.Before) {
		if (!TConfig.highlightCompletedCommissions) return
		if (MC.screenName != "Commissions") return
		val stack = event.slot.item
		if (stack.loreAccordingToNbt.any { it.unformattedString == "COMPLETED" }) {
			event.highlight(Firnauhi.identifier("completed_commission_background"))
		}
	}
}
