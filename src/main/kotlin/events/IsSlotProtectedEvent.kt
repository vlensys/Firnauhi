package moe.nea.firnauhi.events

import net.minecraft.world.item.ItemStack
import net.minecraft.world.inventory.Slot
import net.minecraft.world.inventory.ClickType
import moe.nea.firnauhi.util.CommonSoundEffects
import moe.nea.firnauhi.util.MC
import moe.nea.firnauhi.util.grey
import moe.nea.firnauhi.util.hover
import moe.nea.firnauhi.util.red
import moe.nea.firnauhi.util.tr

data class IsSlotProtectedEvent(
    val slot: Slot?,
    val actionType: ClickType,
    var isProtected: Boolean,
    val itemStackOverride: ItemStack?,
    val origin: MoveOrigin,
    var silent: Boolean = false,
) : FirnauhiEvent() {
	val itemStack get() = itemStackOverride ?: slot!!.item

	fun protect() {
		if (!isProtected) {
			silent = false
		}
		isProtected = true
	}

	fun protectSilent() {
		if (!isProtected) {
			silent = true
		}
		isProtected = true
	}

	enum class MoveOrigin {
		DROP_FROM_HOTBAR,
		SALVAGE,
		INVENTORY_MOVE
		;
	}

	companion object : FirnauhiEventBus<IsSlotProtectedEvent>() {
		@JvmStatic
		@JvmOverloads
		fun shouldBlockInteraction(
            slot: Slot?, action: ClickType,
            origin: MoveOrigin,
            itemStackOverride: ItemStack? = null,
		): Boolean {
			if (slot == null && itemStackOverride == null) return false
			val event = IsSlotProtectedEvent(slot, action, false, itemStackOverride, origin)
			publish(event)
			if (event.isProtected && !event.silent) {
				MC.sendChat(tr("firnauhi.protectitem", "Firnauhi protected your item: ${event.itemStack.hoverName}.\n")
					            .red()
					            .append(tr("firnauhi.protectitem.hoverhint", "Hover for more info.").grey())
					            .hover(tr("firnauhi.protectitem.hint",
					                      "To unlock this item use the Lock Slot or Lock Item keybind from Firnauhi while hovering over this item. If this is a bound slot, you can use disable the Lock Bound Slots setting.")))
				CommonSoundEffects.playFailure()
			}
			return event.isProtected
		}
	}
}
