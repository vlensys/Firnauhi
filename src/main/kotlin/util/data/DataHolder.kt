package moe.nea.firnauhi.util.data

import kotlinx.serialization.KSerializer
import moe.nea.firnauhi.gui.config.storage.ConfigStorageClass

abstract class DataHolder<T>(
	serializer: KSerializer<T>,
	name: String,
	default: () -> T
) : GenericConfig<T>(name, serializer, default) {
	override val storageClass: ConfigStorageClass
		get() = ConfigStorageClass.STORAGE
}
