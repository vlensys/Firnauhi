@file:UseSerializers(IdentifierSerializer::class, FirnauhiRootPredicateSerializer::class)

package moe.nea.firnauhi.features.texturepack


import java.util.concurrent.CompletableFuture
import org.slf4j.LoggerFactory
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlin.jvm.optionals.getOrNull
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.server.packs.resources.SimplePreparableReloadListener
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import net.minecraft.util.profiling.ProfilerFiller
import moe.nea.firnauhi.Firnauhi
import moe.nea.firnauhi.annotations.Subscribe
import moe.nea.firnauhi.events.CustomItemModelEvent
import moe.nea.firnauhi.events.EarlyResourceReloadEvent
import moe.nea.firnauhi.events.FinalizeResourceManagerEvent
import moe.nea.firnauhi.events.ScreenChangeEvent
import moe.nea.firnauhi.util.ErrorUtil
import moe.nea.firnauhi.util.IdentifierSerializer
import moe.nea.firnauhi.util.MC
import moe.nea.firnauhi.util.json.SingletonSerializableList
import moe.nea.firnauhi.util.runNull

object CustomGlobalTextures : SimplePreparableReloadListener<CustomGlobalTextures.CustomGuiTextureOverride>() {
	class CustomGuiTextureOverride(
		val classes: List<ItemOverrideCollection>
	)

	@Serializable
	data class GlobalItemOverride(
        val screen: @Serializable(SingletonSerializableList::class) List<Identifier>,
        val model: Identifier,
        val predicate: FirnauhiModelPredicate,
	)

	@Serializable
	data class ScreenFilter(
		val title: StringMatcher,
	)

	data class ItemOverrideCollection(
		val screenFilter: ScreenFilter,
		val overrides: List<GlobalItemOverride>,
	)

	@Subscribe
	fun onStart(event: FinalizeResourceManagerEvent) {
		MC.resourceManager.registerReloadListener(this)
	}

	@Subscribe
	fun onEarlyReload(event: EarlyResourceReloadEvent) {
		preparationFuture = CompletableFuture
			.supplyAsync(
				{
					prepare(event.resourceManager)
				}, event.preparationExecutor
			)
	}

	@Volatile
	var preparationFuture: CompletableFuture<CustomGuiTextureOverride> = CompletableFuture.completedFuture(
		CustomGuiTextureOverride(listOf())
	)

	override fun prepare(manager: ResourceManager, profiler: ProfilerFiller): CustomGuiTextureOverride {
		return preparationFuture.join()
	}

	override fun apply(prepared: CustomGuiTextureOverride, manager: ResourceManager, profiler: ProfilerFiller) {
		guiClassOverrides = prepared
	}

	val logger = LoggerFactory.getLogger(CustomGlobalTextures::class.java)
	fun prepare(manager: ResourceManager): CustomGuiTextureOverride {
		val overrideResources =
			manager.listResources("overrides/item") { it.namespace == "firmskyblock" && it.path.endsWith(".json") }
				.mapNotNull {
					Firnauhi.tryDecodeJsonFromStream<GlobalItemOverride>(it.value.open()).getOrElse { ex ->
						ErrorUtil.softError("Failed to load global item override at ${it.key}", ex)
						null
					}
				}

		val byGuiClass = overrideResources.flatMap { override -> override.screen.toSet().map { it to override } }
			.groupBy { it.first }
		val guiClasses = byGuiClass.entries
			.mapNotNull {
				val key = it.key
				val guiClassResource =
					manager.getResource(Identifier.fromNamespaceAndPath(key.namespace, "filters/screen/${key.path}.json"))
						.getOrNull()
						?: return@mapNotNull runNull {
							ErrorUtil.softError("Failed to locate screen filter at $key used by ${it.value.map { it.first }}")
						}
				val screenFilter =
					Firnauhi.tryDecodeJsonFromStream<ScreenFilter>(guiClassResource.open())
						.getOrElse { ex ->
							ErrorUtil.softError(
								"Failed to load screen filter at $key used by ${it.value.map { it.first }}",
								ex
							)
							return@mapNotNull null
						}
				ItemOverrideCollection(screenFilter, it.value.map { it.second })
			}
		logger.info("Loaded ${overrideResources.size} global item overrides")
		return CustomGuiTextureOverride(guiClasses)
	}

	var guiClassOverrides = CustomGuiTextureOverride(listOf())

	var matchingOverrides: Set<ItemOverrideCollection> = setOf()

	@Subscribe
	fun onOpenGui(event: ScreenChangeEvent) {
		val newTitle = event.new?.title ?: Component.empty()
		matchingOverrides = guiClassOverrides.classes
			.filterTo(mutableSetOf()) { it.screenFilter.title.matches(newTitle) }
	}

	@Subscribe
	fun replaceGlobalModel(event: CustomItemModelEvent) {
		val override = matchingOverrides
			.firstNotNullOfOrNull {
				it.overrides
					.asSequence()
					.filter { it.predicate.test(event.itemStack) }
					.map { it.model }
					.firstOrNull()
			}

		if (override != null)
			event.overrideIfExists(override)
	}


}
