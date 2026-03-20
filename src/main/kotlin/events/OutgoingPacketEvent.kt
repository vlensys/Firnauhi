

package moe.nea.firnauhi.events

import net.minecraft.network.protocol.Packet

data class OutgoingPacketEvent(val packet: Packet<*>) : FirnauhiEvent.Cancellable() {
    companion object : FirnauhiEventBus<OutgoingPacketEvent>()
}
