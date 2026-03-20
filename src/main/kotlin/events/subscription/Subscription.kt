
package moe.nea.firnauhi.events.subscription

import moe.nea.firnauhi.events.FirnauhiEvent
import moe.nea.firnauhi.events.FirnauhiEventBus


data class Subscription<T : FirnauhiEvent>(
    val owner: Any,
    val invoke: (T) -> Unit,
    val eventBus: FirnauhiEventBus<T>,
    val methodName: String,
)
