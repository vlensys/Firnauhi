package moe.nea.firnauhi.features.inventory.buttons

import net.minecraft.network.chat.Component
import moe.nea.firnauhi.Firnauhi
import moe.nea.firnauhi.util.ErrorUtil
import moe.nea.firnauhi.util.MC
import moe.nea.firnauhi.util.TemplateUtil

object InventoryButtonTemplates {

	val legacyPrefix = "NEUBUTTONS/"
	val modernPrefix = "MAYBEONEDAYIWILLHAVEMYOWNFORMAT"

	fun loadTemplate(t: String): List<InventoryButton>? {
		val buttons = TemplateUtil.maybeDecodeTemplate<List<String>>(legacyPrefix, t) ?: return null
		return buttons.mapNotNull {
			ErrorUtil.catch<InventoryButton?>("Could not import button") {
				Firnauhi.json.decodeFromString<InventoryButton>(it).also {
					if (it.icon?.startsWith("extra:") == true) {
						MC.sendChat(Component.translatable("firnauhi.inventory-buttons.import-failed"))
					}
				}
			}.or {
				null
			}
		}
	}

	fun saveTemplate(buttons: List<InventoryButton>): String {
		return TemplateUtil.encodeTemplate(legacyPrefix, buttons.map { Firnauhi.json.encodeToString(it) })
	}
}
