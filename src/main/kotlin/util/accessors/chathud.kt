package moe.nea.firnauhi.util.accessors

import net.minecraft.client.gui.components.ChatComponent
import net.minecraft.client.GuiMessage
import moe.nea.firnauhi.mixins.accessor.AccessorChatHud

val ChatComponent.messages: MutableList<GuiMessage>
	get() = (this as AccessorChatHud).messages_firnauhi
