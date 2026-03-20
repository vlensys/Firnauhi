package moe.nea.firnauhi.compat.gender

import net.fabricmc.loader.api.FabricLoader
import moe.nea.firnauhi.util.compatloader.CompatMeta
import moe.nea.firnauhi.util.compatloader.ICompatMeta

@CompatMeta
object Compat : ICompatMeta {
	override fun shouldLoad(): Boolean {
		return FabricLoader.getInstance().isModLoaded("wildfire_gender")
	}

}
