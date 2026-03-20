package moe.nea.firnauhi.events

import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor
import net.minecraft.server.packs.resources.ReloadableResourceManager
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.server.packs.resources.PreparableReloadListener

data class FinalizeResourceManagerEvent(
    val resourceManager: ReloadableResourceManager,
) : FirnauhiEvent() {
	companion object : FirnauhiEventBus<FinalizeResourceManagerEvent>()

	inline fun registerOnApply(name: String, crossinline function: () -> Unit) {
		resourceManager.registerReloadListener(object : PreparableReloadListener {
			override fun reload(
                store: PreparableReloadListener.SharedState,
                prepareExecutor: Executor,
                reloadSynchronizer: PreparableReloadListener.PreparationBarrier,
                applyExecutor: Executor
			): CompletableFuture<Void> {
				return CompletableFuture.completedFuture(Unit)
					.thenCompose(reloadSynchronizer::wait)
					.thenAcceptAsync({ function() }, applyExecutor)
			}

			override fun getName(): String {
				return name
			}
		})
	}
}
