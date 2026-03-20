package moe.nea.firnauhi.features.inventory

import java.net.URI
import net.fabricmc.loader.api.FabricLoader
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds
import net.minecraft.SharedConstants
import net.minecraft.network.chat.ClickEvent
import net.minecraft.network.chat.Component
import moe.nea.firnauhi.Firnauhi
import moe.nea.firnauhi.annotations.Subscribe
import moe.nea.firnauhi.commands.thenExecute
import moe.nea.firnauhi.events.CommandEvent
import moe.nea.firnauhi.events.SkyblockServerUpdateEvent
import moe.nea.firnauhi.repo.RepoManager
import moe.nea.firnauhi.util.MC
import moe.nea.firnauhi.util.SBData
import moe.nea.firnauhi.util.aqua
import moe.nea.firnauhi.util.bold
import moe.nea.firnauhi.util.clickCommand
import moe.nea.firnauhi.util.grey
import moe.nea.firnauhi.util.lime
import moe.nea.firnauhi.util.red
import moe.nea.firnauhi.util.white
import moe.nea.firnauhi.util.yellow

object REIDependencyWarner {
	val reiModId = "roughlyenoughitems"
	val hasREI = FabricLoader.getInstance().isModLoaded(reiModId)
	var sentWarning = false

	fun modrinthLink(slug: String) =
		"https://modrinth.com/mod/$slug/versions?g=${SharedConstants.getCurrentVersion().name()}&l=fabric"

	fun downloadButton(modName: String, modId: String, slug: String): Component {
		val alreadyDownloaded = FabricLoader.getInstance().isModLoaded(modId)
		return Component.literal(" - ")
			.white()
			.append(Component.literal("[").aqua())
			.append(Component.translatable("firnauhi.download", modName)
				        .withStyle { it.withClickEvent(ClickEvent.OpenUrl(URI (modrinthLink(slug)))) }
				        .yellow()
				        .also {
					        if (alreadyDownloaded)
						        it.append(Component.translatable("firnauhi.download.already", modName)
							                  .lime())
				        })
			.append(Component.literal("]").aqua())
	}

	@Subscribe
	fun checkREIDependency(event: SkyblockServerUpdateEvent) {
		if (!SBData.isOnSkyblock) return
		if (!RepoManager.TConfig.warnForMissingItemListMod) return
		if (hasREI) return
		if (sentWarning) return
		sentWarning = true
		Firnauhi.coroutineScope.launch {
			delay(2.seconds)
			// TODO: should we offer an automatic install that actually downloads the JARs and places them into the mod folder?
			MC.sendChat(
				Component.translatable("firnauhi.reiwarning").red().bold().append("\n")
					.append(downloadButton("RoughlyEnoughItems", reiModId, "rei")).append("\n")
					.append(downloadButton("Architectury API", "architectury", "architectury-api")).append("\n")
					.append(downloadButton("Cloth Config API", "cloth-config", "cloth-config")).append("\n")
					.append(Component.translatable("firnauhi.reiwarning.disable")
						        .clickCommand("/firm disablereiwarning")
						        .grey())
			)
		}
	}

	@Subscribe
	fun onSubcommand(event: CommandEvent.SubCommand) {
		if (hasREI) return
		event.subcommand("disablereiwarning") {
			thenExecute {
				RepoManager.TConfig.warnForMissingItemListMod = false
				RepoManager.TConfig.markDirty()
				MC.sendChat(Component.translatable("firnauhi.reiwarning.disabled").yellow())
			}
		}
	}
}
