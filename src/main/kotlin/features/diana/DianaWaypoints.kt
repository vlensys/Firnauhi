package moe.nea.firnauhi.features.diana

import moe.nea.firnauhi.annotations.Subscribe
import moe.nea.firnauhi.events.AttackBlockEvent
import moe.nea.firnauhi.events.UseBlockEvent
import moe.nea.firnauhi.util.data.Config
import moe.nea.firnauhi.util.data.ManagedConfig

object DianaWaypoints {
	val identifier get() = "diana"

	@Config
	object TConfig : ManagedConfig(identifier, Category.EVENTS) {
		val ancestralSpadeSolver by toggle("ancestral-spade") { true }
		val ancestralSpadeTeleport by keyBindingWithDefaultUnbound("ancestral-teleport")
		val nearbyWaypoints by toggle("nearby-waypoints") { true }
	}


	@Subscribe
	fun onBlockUse(event: UseBlockEvent) {
		NearbyBurrowsSolver.onBlockClick(event.hitResult.blockPos)
	}

	@Subscribe
	fun onBlockAttack(event: AttackBlockEvent) {
		NearbyBurrowsSolver.onBlockClick(event.blockPos)
	}
}


