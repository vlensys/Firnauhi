

package moe.nea.firnauhi.events

/**
 * An event that can be fired by a [FirnauhiEventBus].
 *
 * Typically, that event bus is implemented as a companion object
 *
 * ```
 * class SomeEvent : FirnauhiEvent() {
 *     companion object : FirnauhiEventBus<SomeEvent>()
 * }
 * ```
 */
abstract class FirnauhiEvent {
    /**
     * A [FirnauhiEvent] that can be [cancelled]
     */
    abstract class Cancellable : FirnauhiEvent() {
        /**
         * Cancels this is event.
         *
         * @see cancelled
         */
        fun cancel() {
            cancelled = true
        }

        /**
         * Whether this event is cancelled.
         *
         * Cancelled events will bypass handlers unless otherwise specified and will prevent the action that this
         * event was originally fired for.
         */
        var cancelled: Boolean = false
    }
}
