package moe.nea.firnauhi.events

data class WorldMouseMoveEvent(val deltaX: Double, val deltaY: Double) : FirnauhiEvent.Cancellable() {
	companion object : FirnauhiEventBus<WorldMouseMoveEvent>()
}
