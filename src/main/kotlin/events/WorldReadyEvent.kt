package moe.nea.firnauhi.events

class WorldReadyEvent : FirnauhiEvent() {
	companion object : FirnauhiEventBus<WorldReadyEvent>()
//	class FullyLoaded : FirnauhiEvent() {
//		companion object : FirnauhiEventBus<FullyLoaded>() {
//			 TODO: check WorldLoadingState
//		}
//	}
}
