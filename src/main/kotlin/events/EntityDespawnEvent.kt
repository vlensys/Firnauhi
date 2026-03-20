
package moe.nea.firnauhi.events

import net.minecraft.world.entity.Entity

data class EntityDespawnEvent(
    val entity: Entity?, val entityId: Int,
    val reason: Entity.RemovalReason,
) : FirnauhiEvent() {
    companion object: FirnauhiEventBus<EntityDespawnEvent>()
}
