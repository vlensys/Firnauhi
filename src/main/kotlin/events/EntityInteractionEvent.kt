
package moe.nea.firnauhi.events

import net.minecraft.world.entity.Entity
import net.minecraft.world.InteractionHand

data class EntityInteractionEvent(
    val kind: InteractionKind,
    val entity: Entity,
    val hand: InteractionHand,
) : FirnauhiEvent() {
    companion object : FirnauhiEventBus<EntityInteractionEvent>()
    enum class InteractionKind {
        /**
         * Is sent when left-clicking an entity
         */
        ATTACK,

        /**
         * Is a fallback when [INTERACT_AT_LOCATION] fails
         */
        INTERACT,

        /**
         * Is tried first on right click
         */
        INTERACT_AT_LOCATION,
    }
}
