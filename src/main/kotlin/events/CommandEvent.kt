

package moe.nea.firnauhi.events

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.tree.LiteralCommandNode
import net.minecraft.commands.CommandBuildContext
import moe.nea.firnauhi.commands.CaseInsensitiveLiteralCommandNode
import moe.nea.firnauhi.commands.DefaultSource
import moe.nea.firnauhi.commands.literal
import moe.nea.firnauhi.commands.thenLiteral

data class CommandEvent(
    val dispatcher: CommandDispatcher<DefaultSource>,
    val ctx: CommandBuildContext,
    val serverCommands: CommandDispatcher<*>?,
) : FirnauhiEvent() {
    companion object : FirnauhiEventBus<CommandEvent>()

    /**
     * Register subcommands to `/firm`. For new top level commands use [CommandEvent]. Cannot be used to register
     * subcommands to other commands.
     */
    data class SubCommand(
        val builder: CaseInsensitiveLiteralCommandNode.Builder<DefaultSource>,
        val commandRegistryAccess: CommandBuildContext,
    ) : FirnauhiEvent() {
        companion object : FirnauhiEventBus<SubCommand>()

        fun subcommand(name: String, block: CaseInsensitiveLiteralCommandNode.Builder<DefaultSource>.() -> Unit) {
            builder.thenLiteral(name, block)
        }
    }

    fun deleteCommand(name: String) {
        dispatcher.root.children.removeIf { it.name.equals(name, ignoreCase = false) }
        serverCommands?.root?.children?.removeIf { it.name.equals(name, ignoreCase = false) }
    }

    fun register(
        name: String,
        block: CaseInsensitiveLiteralCommandNode.Builder<DefaultSource>.() -> Unit
    ): LiteralCommandNode<DefaultSource> {
        return dispatcher.register(literal(name, block))
    }
}
