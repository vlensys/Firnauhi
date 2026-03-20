package moe.nea.firnauhi.compat.rei

import me.shedaniel.rei.api.common.entry.type.EntryTypeRegistry
import me.shedaniel.rei.api.common.plugins.REICommonPlugin
import moe.nea.firnauhi.repo.RepoManager

class FirnauhiReiCommonPlugin : REICommonPlugin {
	override fun registerEntryTypes(registry: EntryTypeRegistry) {
		if (!RepoManager.shouldLoadREI()) return
		registry.register(FirnauhiReiPlugin.SKYBLOCK_ITEM_TYPE_ID, SBItemEntryDefinition)
	}
}
