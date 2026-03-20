package moe.nea.firnauhi.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.fabric.impl.command.client.ClientCommandInternals;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientCommandInternals.class)
public class AlwaysDisplayFirnauhiClientCommandErrors {
	@ModifyExpressionValue(method = "executeCommand", at = @At(value = "INVOKE", target = "Lnet/fabricmc/fabric/impl/command/client/ClientCommandInternals;isIgnoredException(Lcom/mojang/brigadier/exceptions/CommandExceptionType;)Z"))
	private static boolean markFirnauhiExceptionsAsNotIgnores(boolean original, @Local(argsOnly = true) String command) {
		if (command.startsWith("firm ") || command.equals("firm") || command.startsWith("firnauhi ") || command.equals("firnauhi")) {
			return false;
		}
		return original;
	}
}
