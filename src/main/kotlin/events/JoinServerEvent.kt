package moe.nea.firnauhi.events

import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.minecraft.client.multiplayer.ClientPacketListener

data class JoinServerEvent(
    val networkHandler: ClientPacketListener,
    val packetSender: PacketSender,
) : FirnauhiEvent() {
	companion object : FirnauhiEventBus<JoinServerEvent>()
}
