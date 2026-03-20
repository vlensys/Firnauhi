package moe.nea.firnauhi.features.inventory

import moe.nea.firnauhi.annotations.Subscribe
import moe.nea.firnauhi.events.HandledScreenKeyPressedEvent
import moe.nea.firnauhi.repo.ExpensiveItemCacheApi
import moe.nea.firnauhi.repo.HypixelStaticData
import moe.nea.firnauhi.repo.ItemCache.asItemStack
import moe.nea.firnauhi.repo.ItemCache.isBroken
import moe.nea.firnauhi.repo.RepoManager
import moe.nea.firnauhi.util.MC
import moe.nea.firnauhi.util.asBazaarStock
import moe.nea.firnauhi.util.data.Config
import moe.nea.firnauhi.util.data.ManagedConfig
import moe.nea.firnauhi.util.focusedItemStack
import moe.nea.firnauhi.util.skyBlockId
import moe.nea.firnauhi.util.skyblock.SBItemUtil.getSearchName

object ItemHotkeys {
	@Config
	object TConfig : ManagedConfig("item-hotkeys", Category.INVENTORY) {
		val openGlobalTradeInterface by keyBindingWithDefaultUnbound("global-trade-interface")
	}

	@OptIn(ExpensiveItemCacheApi::class)
	@Subscribe
	fun onHandledInventoryPress(event: HandledScreenKeyPressedEvent) {
		if (!event.matches(TConfig.openGlobalTradeInterface)) {
			return
		}
		var item = event.screen.focusedItemStack ?: return
		val skyblockId = item.skyBlockId ?: return
		item = RepoManager.getNEUItem(skyblockId)?.asItemStack()?.takeIf { !it.isBroken } ?: item
		if (HypixelStaticData.hasBazaarStock(skyblockId.asBazaarStock)) {
			MC.sendCommand("bz ${item.getSearchName()}")
		} else if (HypixelStaticData.hasAuctionHouseOffers(skyblockId)) {
			MC.sendCommand("ahs ${item.getSearchName()}")
		} else {
			return
		}
		event.cancel()
	}

}
