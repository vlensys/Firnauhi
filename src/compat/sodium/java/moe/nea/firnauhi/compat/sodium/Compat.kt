package moe.nea.firnauhi.compat.sodium

import moe.nea.firnauhi.util.compatloader.CompatMeta
import moe.nea.firnauhi.util.compatloader.ICompatMeta
import net.fabricmc.loader.api.FabricLoader

@CompatMeta
object Compat : ICompatMeta {
	override fun shouldLoad(): Boolean {
		return FabricLoader.getInstance().isModLoaded("sodium")
	}
}
