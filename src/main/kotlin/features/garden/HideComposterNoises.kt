package moe.nea.firnauhi.features.garden

import net.minecraft.world.entity.animal.wolf.WolfSoundVariants
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import moe.nea.firnauhi.annotations.Subscribe
import moe.nea.firnauhi.events.SoundReceiveEvent
import moe.nea.firnauhi.util.SBData
import moe.nea.firnauhi.util.SkyBlockIsland
import moe.nea.firnauhi.util.data.Config
import moe.nea.firnauhi.util.data.ManagedConfig

object HideComposterNoises {
	@Config
	object TConfig : ManagedConfig("composter", Category.GARDEN) {
		val hideComposterNoises by toggle("no-more-noises") { false }
	}

	val composterSoundEvents: List<SoundEvent> = listOf(
		SoundEvents.PISTON_EXTEND,
		SoundEvents.WATER_AMBIENT,
		SoundEvents.CHICKEN_EGG,
		SoundEvents.WOLF_SOUNDS[WolfSoundVariants.SoundSet.CLASSIC]!!.growlSound().value(),
	)

	@Subscribe
	fun onNoise(event: SoundReceiveEvent) {
		if (!TConfig.hideComposterNoises) return
		if (SBData.skyblockLocation == SkyBlockIsland.GARDEN) {
			if (event.sound.value() in composterSoundEvents)
				event.cancel()
		}
	}
}
