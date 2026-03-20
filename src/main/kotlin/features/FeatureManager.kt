package moe.nea.firnauhi.features

import moe.nea.firnauhi.events.FirnauhiEvent
import moe.nea.firnauhi.events.subscription.Subscription
import moe.nea.firnauhi.events.subscription.SubscriptionList
import moe.nea.firnauhi.util.ErrorUtil
import moe.nea.firnauhi.util.compatloader.ICompatMeta

object FeatureManager {

	fun subscribeEvents() {
		SubscriptionList.allLists.forEach { list ->
			if (ICompatMeta.shouldLoad(list.javaClass.name))
				ErrorUtil.catch("Error while loading events from $list") {
					list.provideSubscriptions {
						subscribeSingleEvent(it)
					}
				}
		}
	}

	private fun <T : FirnauhiEvent> subscribeSingleEvent(it: Subscription<T>) {
		it.eventBus.subscribe(false, "${it.owner.javaClass.simpleName}:${it.methodName}", it.invoke)
	}
}
