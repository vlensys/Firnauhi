package moe.nea.firnauhi.gui.config.storage

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import moe.nea.firnauhi.events.FirnauhiEvent
import moe.nea.firnauhi.events.FirnauhiEventBus

data class ConfigFixEvent(
	val storageClass: ConfigStorageClass,
	val toVersion: Int,
	var data: JsonObject,
) : FirnauhiEvent() {
	companion object : FirnauhiEventBus<ConfigFixEvent>() {

	}
	fun on(
		toVersion: Int,
		storageClass: ConfigStorageClass,
		block: ConfigEditor.() -> Unit
	) {
		require(toVersion <= FirnauhiConfigLoader.currentConfigVersion)
		if (this.toVersion == toVersion && this.storageClass == storageClass) {
			block(ConfigEditor(listOf(object : JsonPointer {
				override fun get(): JsonObject {
					return data
				}

				override fun set(value: JsonElement) {
					data = value as JsonObject
				}

				override fun toString(): String {
					return "ConfigRoot($storageClass)"
				}
			})))
		}
	}
}
