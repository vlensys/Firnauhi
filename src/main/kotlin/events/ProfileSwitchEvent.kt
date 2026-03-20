package moe.nea.firnauhi.events

import java.util.UUID

data class ProfileSwitchEvent(val oldProfile: UUID?, val newProfile: UUID?) : FirnauhiEvent() {
	companion object : FirnauhiEventBus<ProfileSwitchEvent>()
}
