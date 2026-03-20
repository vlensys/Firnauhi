
package moe.nea.firnauhi.events

import java.util.concurrent.Executor
import net.minecraft.server.packs.resources.ResourceManager

data class EarlyResourceReloadEvent(val resourceManager: ResourceManager, val preparationExecutor: Executor) :
    FirnauhiEvent() {
    companion object : FirnauhiEventBus<EarlyResourceReloadEvent>()
}
