package moe.nea.firnauhi.events

import io.github.moulberry.repo.NEURepository

data class ReloadRegistrationEvent(val repo: NEURepository) : FirnauhiEvent() {
    companion object : FirnauhiEventBus<ReloadRegistrationEvent>()
}
