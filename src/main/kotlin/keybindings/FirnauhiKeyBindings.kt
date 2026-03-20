package moe.nea.firnauhi.keybindings

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.KeyMapping
import com.mojang.blaze3d.platform.InputConstants
import moe.nea.firnauhi.Firnauhi
import moe.nea.firnauhi.gui.config.ManagedOption
import moe.nea.firnauhi.util.TestUtil
import moe.nea.firnauhi.util.data.ManagedConfig

object FirnauhiKeyBindings {
	val cats = mutableMapOf<ManagedConfig.Category, KeyMapping.Category>()


	fun registerKeyBinding(name: String, config: ManagedOption<SavedKeyBinding>) {
		val vanillaKeyBinding = KeyMapping(
			name,
			InputConstants.Type.KEYSYM,
			-1,
			cats.computeIfAbsent(config.element.category) {
				KeyMapping.Category.register(Firnauhi.identifier(it.name.lowercase()))
			}
		)
		if (!TestUtil.isInTest) {
			KeyBindingHelper.registerKeyBinding(vanillaKeyBinding)
		}
		keyBindings[vanillaKeyBinding] = config
	}

	val keyBindings = mutableMapOf<KeyMapping, ManagedOption<SavedKeyBinding>>()

}
