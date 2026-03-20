package moe.nea.firnauhi.compat.yacl

import dev.isxander.yacl3.api.Controller
import dev.isxander.yacl3.api.Option
import dev.isxander.yacl3.api.controller.ControllerBuilder
import moe.nea.firnauhi.gui.config.ManagedOption
import moe.nea.firnauhi.keybindings.SavedKeyBinding

class KeybindingBuilder(
	val option: Option<SavedKeyBinding>,
	val managedOption: ManagedOption<SavedKeyBinding>
) : ControllerBuilder<SavedKeyBinding> {
	override fun build(): Controller<SavedKeyBinding> {
		return KeybindingController(option, managedOption)
	}
}
