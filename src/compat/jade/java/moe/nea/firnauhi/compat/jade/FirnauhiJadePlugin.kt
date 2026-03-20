package moe.nea.firnauhi.compat.jade

import snownee.jade.api.IWailaClientRegistration
import snownee.jade.api.IWailaCommonRegistration
import snownee.jade.api.IWailaPlugin
import snownee.jade.api.WailaPlugin
import net.minecraft.world.level.block.Block
import moe.nea.firnauhi.Firnauhi

@WailaPlugin
class FirnauhiJadePlugin : IWailaPlugin {
	override fun register(registration: IWailaCommonRegistration) {
		Firnauhi.logger.debug("Registering Jade integration...")
	}

	override fun registerClient(registration: IWailaClientRegistration) {
		registration.registerBlockComponent(CustomMiningHardnessProvider, Block::class.java)
		registration.registerBlockComponent(DrillToolProvider(), Block::class.java)
		registration.addRayTraceCallback(CustomFakeBlockProvider(registration))
	}
}
