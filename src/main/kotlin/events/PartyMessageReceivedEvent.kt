package moe.nea.firnauhi.events

data class PartyMessageReceivedEvent(
	val from: ProcessChatEvent,
	val message: String,
	val name: String,
) : FirnauhiEvent() {
	companion object : FirnauhiEventBus<PartyMessageReceivedEvent>()
}
