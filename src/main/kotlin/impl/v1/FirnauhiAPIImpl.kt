package moe.nea.firnauhi.impl.v1

import java.util.Collections
import java.util.Optional
import net.fabricmc.loader.api.FabricLoader
import kotlin.jvm.optionals.getOrNull
import net.minecraft.world.item.ItemStack
import moe.nea.firnauhi.Firnauhi
import moe.nea.firnauhi.api.v1.FirnauhiAPI
import moe.nea.firnauhi.api.v1.FirnauhiExtension
import moe.nea.firnauhi.api.v1.FirnauhiItemWidget
import moe.nea.firnauhi.features.items.recipes.ItemList
import moe.nea.firnauhi.repo.ExpensiveItemCacheApi
import moe.nea.firnauhi.util.MC
import moe.nea.firnauhi.util.intoOptional

object FirnauhiAPIImpl : FirnauhiAPI() {
	@JvmField
	val INSTANCE: FirnauhiAPI = FirnauhiAPIImpl

	private val _extensions = mutableListOf<FirnauhiExtension>()
	override fun getExtensions(): List<FirnauhiExtension> {
		return Collections.unmodifiableList(_extensions)
	}

	@OptIn(ExpensiveItemCacheApi::class)
	override fun getHoveredItemWidget(): Optional<FirnauhiItemWidget> {
		val mouse = MC.instance.mouseHandler
		val window = MC.window
		val xpos = mouse.getScaledXPos(window)
		val ypos = mouse.getScaledYPos(window)
		val widget = MC.screen
			?.getChildAt(xpos, ypos)
			?.getOrNull()
		if (widget is FirnauhiItemWidget) return widget.intoOptional()
		val itemListStack = ItemList.findStackUnder(xpos.toInt(), ypos.toInt())
		if (itemListStack != null)
			return object : FirnauhiItemWidget {
				override fun getPlacement(): FirnauhiItemWidget.Placement {
					return FirnauhiItemWidget.Placement.ITEM_LIST
				}

				override fun getItemStack(): ItemStack {
					return itemListStack.second.asImmutableItemStack()
				}

				override fun getSkyBlockId(): String {
					return itemListStack.second.skyblockId.neuItem
				}

			}.intoOptional()
		return Optional.empty()
	}

	fun loadExtensions() {
		for (container in FabricLoader.getInstance()
			.getEntrypointContainers(FirnauhiExtension.ENTRYPOINT_NAME, FirnauhiExtension::class.java)) {
			Firnauhi.logger.info("Loading extension ${container.entrypoint} from ${container.provider.metadata.name}")
			loadExtension(container.entrypoint)
		}
		extensions.forEach { it.onLoad() }
	}

	fun loadExtension(entrypoint: FirnauhiExtension) {
		_extensions.add(entrypoint)
	}
}
