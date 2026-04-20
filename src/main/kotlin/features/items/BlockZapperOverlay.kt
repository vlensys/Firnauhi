package moe.nea.firnauhi.features.items

import io.github.notenoughupdates.moulconfig.ChromaColour
import java.util.ArrayDeque
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.HitResult
import net.minecraft.core.BlockPos
import moe.nea.firnauhi.annotations.Subscribe
import moe.nea.firnauhi.events.WorldKeyboardEvent
import moe.nea.firnauhi.events.WorldRenderLastEvent
import moe.nea.firnauhi.util.MC
import moe.nea.firnauhi.util.data.Config
import moe.nea.firnauhi.util.data.ManagedConfig
import moe.nea.firnauhi.util.render.RenderInWorldContext
import moe.nea.firnauhi.util.skyBlockId
import moe.nea.firnauhi.util.skyblock.SkyBlockItems

object BlockZapperOverlay {
	val identifier: String
		get() = "block-zapper-overlay"

	@Config
	object TConfig : ManagedConfig(identifier, Category.ITEMS) {
		var blockZapperOverlay by toggle("block-zapper-overlay") { false }
		val color by colour("color") { ChromaColour.fromStaticRGB(160, 0, 0, 60) }
		var undoKey by keyBindingWithDefaultUnbound("undo-key")
	}

	val bannedZapper: Set<Block> = setOf(
		Blocks.WHEAT,
		Blocks.CARROTS,
		Blocks.POTATOES,
		Blocks.PUMPKIN,
		Blocks.PUMPKIN_STEM,
		Blocks.MELON,
		Blocks.MELON_STEM,
		Blocks.CACTUS,
		Blocks.SUGAR_CANE,
		Blocks.NETHER_WART,
		Blocks.TALL_GRASS,
		Blocks.SUNFLOWER,
		Blocks.FARMLAND,
		Blocks.BREWING_STAND,
		Blocks.SNOW,
		Blocks.RED_MUSHROOM,
		Blocks.BROWN_MUSHROOM,
	)

	private val zapperOffsets: List<BlockPos> = listOf(
		BlockPos(0, 0, -1),
		BlockPos(0, 0, 1),
		BlockPos(-1, 0, 0),
		BlockPos(1, 0, 0),
		BlockPos(0, 1, 0),
		BlockPos(0, -1, 0)
	)
	private var cachedAtTick = -1
	private var cachedTargetPos: BlockPos? = null
	private var cachedTargetBlock: Block? = null
	private var cachedTargetWorld: net.minecraft.world.level.Level? = null
	private var cachedBlocks: Set<BlockPos> = emptySet()

	private fun recalculateBlocks(world: net.minecraft.world.level.Level, hitPos: BlockPos): Set<BlockPos> {
		val firstBlockState: BlockState = world.getBlockState(hitPos)
		val block = firstBlockState.block
		val initialAboveBlock = world.getBlockState(hitPos.above()).block
		if (initialAboveBlock in bannedZapper || block in bannedZapper) return emptySet()

		val zapperBlocks = HashSet<BlockPos>(192)
		val returnablePositions = ArrayDeque<BlockPos>()
		var pos = hitPos
		var i = 0
		while (i < 164) {
			zapperBlocks.add(pos)
			returnablePositions.remove(pos)

			var firstNeighbor: BlockPos? = null
			var secondNeighbor: BlockPos? = null
			for (offset in zapperOffsets) {
				val newPos = pos.offset(offset)
				if (newPos in zapperBlocks) continue
				val state = world.getBlockState(newPos)
				if (state.block !== block) continue
				val aboveBlock = world.getBlockState(newPos.above()).block
				if (aboveBlock in bannedZapper) continue
				if (firstNeighbor == null) {
					firstNeighbor = newPos
				} else {
					secondNeighbor = newPos
					break
				}
			}

			if (secondNeighbor != null) {
				returnablePositions.addLast(pos)
				pos = firstNeighbor!!
			} else if (firstNeighbor != null) {
				pos = firstNeighbor
			} else if (returnablePositions.isEmpty()) {
				break
			} else {
				i--
				pos = returnablePositions.removeLast()
			}
			i++
		}
		return zapperBlocks
	}

	// Skidded from NEU
	// Credit: https://github.com/NotEnoughUpdates/NotEnoughUpdates/blob/9b1fcfebc646e9fb69f99006327faa3e734e5f51/src/main/java/io/github/moulberry/notenoughupdates/miscfeatures/CustomItemEffects.java#L1281-L1355 (Modified)
	@Subscribe
	fun renderBlockZapperOverlay(event: WorldRenderLastEvent) {
		if (!TConfig.blockZapperOverlay) return
		val player = MC.player ?: return
		val world = player.level ?: return
		val heldItem = MC.stackInHand
		if (heldItem.skyBlockId != SkyBlockItems.BLOCK_ZAPPER) return
		val hitResult = MC.instance.hitResult ?: return
		if (hitResult is BlockHitResult && hitResult.type == HitResult.Type.BLOCK) {
			val pos = hitResult.blockPos
			val block = world.getBlockState(pos).block
			val shouldRebuild = cachedAtTick != MC.currentTick ||
				cachedTargetWorld !== world ||
				cachedTargetPos != pos ||
				cachedTargetBlock !== block
			if (shouldRebuild) {
				cachedAtTick = MC.currentTick
				cachedTargetWorld = world
				cachedTargetPos = pos.immutable()
				cachedTargetBlock = block
				cachedBlocks = recalculateBlocks(world, pos)
			}
			RenderInWorldContext.renderInWorld(event) {
				if (MC.player?.isShiftKeyDown ?: false) {
					cachedBlocks.forEach {
						block(it, TConfig.color.getEffectiveColourRGB())
					}
				} else {
					sharedVoxelSurface(cachedBlocks, TConfig.color.getEffectiveColourRGB())
				}
			}
		}
	}

	@Subscribe
	fun onWorldKeyboard(it: WorldKeyboardEvent) {
		if (!TConfig.undoKey.isBound) return
		if (!it.matches(TConfig.undoKey)) return
		if (MC.stackInHand.skyBlockId != SkyBlockItems.BLOCK_ZAPPER) return
		MC.sendCommand("undozap")
	}
}
