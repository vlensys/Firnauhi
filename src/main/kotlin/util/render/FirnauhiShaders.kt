package moe.nea.firnauhi.util.render

import moe.nea.firnauhi.annotations.Subscribe
import moe.nea.firnauhi.events.DebugInstantiateEvent

object FirnauhiShaders {

	@Subscribe
	fun debugLoad(event: DebugInstantiateEvent) {
		// TODO: do i still need to work with shaders like this?
	}
}
