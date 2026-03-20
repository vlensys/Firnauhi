package moe.nea.firnauhi.features.texturepack

import moe.nea.firnauhi.util.compatloader.CompatMeta
import moe.nea.firnauhi.util.compatloader.ICompatMeta

@CompatMeta
object Compat : ICompatMeta {
	override fun shouldLoad(): Boolean {
		return true
	}
}
