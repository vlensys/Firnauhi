package moe.nea.firnauhi.util.skyblock

import net.minecraft.world.item.ItemStack
import moe.nea.firnauhi.util.mc.loreAccordingToNbt
import moe.nea.firnauhi.util.unformattedString

object SBItemUtil {
	fun ItemStack.getSearchName(): String {
		val name = this.hoverName.unformattedString
		if (name.contains("Enchanted Book")) {
			val enchant = loreAccordingToNbt.firstOrNull()?.unformattedString
			if (enchant != null) return enchant
		}
		if (name.startsWith("[Lvl")) {
			val closing = name.indexOf(']')
			if (closing > 0)
				return name.substring(closing)
		}
		return name
	}
}
