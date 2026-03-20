package moe.nea.firnauhi

import com.google.gson.Gson
import com.mojang.brigadier.CommandDispatcher
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.fabricmc.fabric.api.resource.ResourcePackActivationType
import net.fabricmc.loader.api.FabricLoader
import net.fabricmc.loader.api.Version
import net.fabricmc.loader.api.metadata.ModMetadata
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.plus
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlin.coroutines.EmptyCoroutineContext
import net.minecraft.commands.CommandBuildContext
import net.minecraft.resources.Identifier
import moe.nea.firnauhi.commands.registerFirnauhiCommand
import moe.nea.firnauhi.events.ClientInitEvent
import moe.nea.firnauhi.events.ClientStartedEvent
import moe.nea.firnauhi.events.CommandEvent
import moe.nea.firnauhi.events.ItemTooltipEvent
import moe.nea.firnauhi.events.ScreenRenderPostEvent
import moe.nea.firnauhi.events.TickEvent
import moe.nea.firnauhi.events.registration.registerFirnauhiEvents
import moe.nea.firnauhi.features.FeatureManager
import moe.nea.firnauhi.gui.config.storage.FirnauhiConfigLoader
import moe.nea.firnauhi.impl.v1.FirnauhiAPIImpl
import moe.nea.firnauhi.repo.HypixelStaticData
import moe.nea.firnauhi.repo.RepoManager
import moe.nea.firnauhi.util.MC
import moe.nea.firnauhi.util.SBData
import moe.nea.firnauhi.util.mc.InitLevel
import moe.nea.firnauhi.util.tr

object Firnauhi {
	val modContainer by lazy { FabricLoader.getInstance().getModContainer(MOD_ID).get() }
	const val MOD_ID = "firnauhi"

	val DEBUG = System.getProperty("firnauhi.debug") == "true"
	val DATA_DIR: Path = Path.of(".firnauhi").also { Files.createDirectories(it) }
	val logger: Logger = LogManager.getLogger("Firnauhi")
	private val metadata: ModMetadata by lazy {
		FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow().metadata
	}
	val version: Version by lazy { metadata.version }

	private val DEFAULT_JSON_INDENT = "    "

	@OptIn(ExperimentalSerializationApi::class)
	val json = Json {
		prettyPrint = DEBUG
		isLenient = true
		allowTrailingComma = true
		allowComments = true
		ignoreUnknownKeys = true
		encodeDefaults = true
		prettyPrintIndent = if (prettyPrint) "\t" else DEFAULT_JSON_INDENT
	}

	/**
	 * FUCK two space indentation
	 */
	val twoSpaceJson = Json(from = json) {
		prettyPrint = true
		prettyPrintIndent = "  "
	}
	val gson = Gson()
	val tightJson = Json(from = json) {
		prettyPrint = false
		// Reset pretty print indent back to default to prevent getting yelled at by json
		prettyPrintIndent = DEFAULT_JSON_INDENT
		explicitNulls = false
	}

	val globalJob = Job()
	val coroutineScope =
		CoroutineScope(EmptyCoroutineContext + CoroutineName("Firnauhi")) + SupervisorJob(globalJob)

	private fun registerCommands(
		dispatcher: CommandDispatcher<FabricClientCommandSource>,
		@Suppress("UNUSED_PARAMETER")
		ctx: CommandBuildContext
	) {
		registerFirnauhiCommand(dispatcher, ctx)
		CommandEvent.publish(CommandEvent(dispatcher, ctx, MC.networkHandler?.commands))
	}

	@JvmStatic
	fun onInitialize() {
	}

	@JvmStatic
	fun onClientInitialize() {
		InitLevel.bump(InitLevel.MC_INIT)
		FeatureManager.subscribeEvents()
		FirnauhiConfigLoader.loadConfig()
		ClientTickEvents.END_CLIENT_TICK.register(ClientTickEvents.EndTick { instance ->
			TickEvent.publish(TickEvent(MC.currentTick++))
		})
		RepoManager.initialize()
		SBData.init()
		HypixelStaticData.spawnDataCollectionLoop()
		ClientCommandRegistrationCallback.EVENT.register(this::registerCommands)
		ClientLifecycleEvents.CLIENT_STARTED.register(ClientLifecycleEvents.ClientStarted {
			ClientStartedEvent.publish(ClientStartedEvent())
		})
		ClientLifecycleEvents.CLIENT_STOPPING.register(ClientLifecycleEvents.ClientStopping {
			logger.info("Shutting down Firnauhi coroutines")
			globalJob.cancel()
		})
		registerFirnauhiEvents()
		FirnauhiAPIImpl.loadExtensions()
		ItemTooltipCallback.EVENT.register { stack, context, type, lines ->
			ItemTooltipEvent.publish(ItemTooltipEvent(stack, context, type, lines))
		}
		ScreenEvents.AFTER_INIT.register(ScreenEvents.AfterInit { client, screen, scaledWidth, scaledHeight ->
			ScreenEvents.afterRender(screen)
				.register(ScreenEvents.AfterRender { screen, drawContext, mouseX, mouseY, tickDelta ->
					ScreenRenderPostEvent.publish(ScreenRenderPostEvent(screen, mouseX, mouseY, tickDelta, drawContext))
				})
		})
		ClientInitEvent.publish(ClientInitEvent())
		ResourceManagerHelper.registerBuiltinResourcePack(
			identifier("transparent_overlay"),
			modContainer,
			tr("firnauhi.resourcepack.transparentoverlay", "Transparent Firnauhi Overlay"),
			ResourcePackActivationType.NORMAL
		)
	}


	fun identifier(path: String) = Identifier.fromNamespaceAndPath(MOD_ID, path)
	inline fun <reified T : Any> tryDecodeJsonFromStream(inputStream: InputStream): Result<T> {
		return runCatching {
			json.decodeFromStream<T>(inputStream)
		}
	}
}
