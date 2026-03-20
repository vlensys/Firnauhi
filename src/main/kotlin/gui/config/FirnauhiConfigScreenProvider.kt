package moe.nea.firnauhi.gui.config

import net.minecraft.client.gui.screens.Screen
import moe.nea.firnauhi.util.compatloader.CompatLoader

interface FirnauhiConfigScreenProvider {
	val key: String
	val isEnabled: Boolean get() = true

	fun open(search: String?, parent: Screen?): Screen

	companion object : CompatLoader<FirnauhiConfigScreenProvider>(FirnauhiConfigScreenProvider::class) {
		val providers by lazy {
			allValidInstances
				.filter { it.isEnabled }
				.sortedWith(
					Comparator
						.comparing<FirnauhiConfigScreenProvider, Boolean>({ it.key == "builtin" })
						.reversed()
						.then(Comparator.comparing({ it.key }))
				).toList()
		}
	}
}
