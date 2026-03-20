package moe.nea.firnauhi.events

/**
 * Called in a devenv after minecraft has been initialized. This event should be used to force instantiation of lazy
 * variables (and similar late init) to cause any possible issues to materialize.
 */
class DebugInstantiateEvent : FirnauhiEvent() {
	companion object : FirnauhiEventBus<DebugInstantiateEvent>()
}
