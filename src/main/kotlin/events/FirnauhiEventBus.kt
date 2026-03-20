package moe.nea.firnauhi.events

import java.util.concurrent.CopyOnWriteArrayList
import org.apache.commons.lang3.reflect.TypeUtils
import moe.nea.firnauhi.Firnauhi
import moe.nea.firnauhi.util.ErrorUtil
import moe.nea.firnauhi.util.MC

/**
 * A pubsub event bus.
 *
 * [subscribe] to events [publish]ed on this event bus.
 * Subscriptions may not necessarily be delivered in the order of registering.
 */
open class FirnauhiEventBus<T : FirnauhiEvent> {
    companion object {
        val allEventBuses = mutableListOf<FirnauhiEventBus<*>>()
    }

    val eventType = TypeUtils.getTypeArguments(javaClass, FirnauhiEventBus::class.java)!!.values.single()

    init {
        allEventBuses.add(this)
    }

    data class Handler<T>(
        val invocation: (T) -> Unit, val receivesCancelled: Boolean,
        var knownErrors: MutableSet<Class<*>> = mutableSetOf(),
        val label: String,
    )

    private val toHandle: MutableList<Handler<T>> = CopyOnWriteArrayList()
    val handlers: List<Handler<T>> get() = toHandle

    fun subscribe(label: String, handle: (T) -> Unit) {
        subscribe(false, label, handle)
    }

    fun subscribe(receivesCancelled: Boolean, label: String, handle: (T) -> Unit) {
        toHandle.add(Handler(handle, receivesCancelled, label = label))
    }

    fun publish(event: T): T {
        for (function in toHandle) {
            if (function.receivesCancelled || event !is FirnauhiEvent.Cancellable || !event.cancelled) {
                try {
                    function.invocation(event)
                } catch (e: Exception) {
                    val klass = e.javaClass
                    if (!function.knownErrors.contains(klass) || Firnauhi.DEBUG) {
                        function.knownErrors.add(klass)
                        ErrorUtil.softError("Caught exception during processing event $event by $function", e)
                    }
                }
            }
        }
        return event
    }

    fun publishSync(event: T) {
        MC.onMainThread {
            publish(event)
        }
    }
}
