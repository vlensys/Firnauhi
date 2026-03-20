package moe.nea.firnauhi.mixins;

import moe.nea.firnauhi.events.HandledScreenKeyPressedEvent;
import moe.nea.firnauhi.keybindings.GenericInputAction;
import moe.nea.firnauhi.keybindings.InputModifiers;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Screen.class)
public class ScreenInputEvents {
	@Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
	public void onKeyPressed(KeyEvent input, CallbackInfoReturnable<Boolean> cir) {
		if (HandledScreenKeyPressedEvent.Companion.publish(new HandledScreenKeyPressedEvent(
			(Screen) (Object) this,
			GenericInputAction.of(input),
			InputModifiers.of(input))).getCancelled()) {
			cir.setReturnValue(true);
		}
	}

	public boolean onMouseClicked$firnauhi(MouseButtonEvent click, boolean doubled) {
		return HandledScreenKeyPressedEvent.Companion.publish(
			new HandledScreenKeyPressedEvent((Screen) (Object) this,
				GenericInputAction.mouse(click), InputModifiers.current())).getCancelled();
	}


}
