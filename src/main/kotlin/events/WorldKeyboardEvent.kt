package moe.nea.firnauhi.events

import moe.nea.firnauhi.keybindings.GenericInputAction
import moe.nea.firnauhi.keybindings.InputModifiers
import moe.nea.firnauhi.keybindings.SavedKeyBinding

data class WorldKeyboardEvent(val action: GenericInputAction, val modifiers: InputModifiers) : FirnauhiEvent.Cancellable() {
	fun matches(keyBinding: SavedKeyBinding, atLeast: Boolean = false): Boolean {
		return keyBinding.matches(action, modifiers, atLeast)
	}

	companion object : FirnauhiEventBus<WorldKeyboardEvent>()
}
