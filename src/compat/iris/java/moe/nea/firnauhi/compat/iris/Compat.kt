package moe.nea.firnauhi.compat.iris

import net.fabricmc.loader.api.FabricLoader
import moe.nea.firnauhi.util.compatloader.CompatMeta
import moe.nea.firnauhi.util.compatloader.ICompatMeta

@CompatMeta
object Compat : ICompatMeta {
	override fun shouldLoad(): Boolean {
		return FabricLoader.getInstance().isModLoaded("iris")
	}
}
