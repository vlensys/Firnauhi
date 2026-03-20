package moe.nea.firnauhi.features.texturepack

import com.google.gson.JsonObject
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import moe.nea.firnauhi.features.texturepack.predicates.AndPredicate
import moe.nea.firnauhi.util.json.intoGson

object FirnauhiRootPredicateSerializer : KSerializer<FirnauhiModelPredicate> {
	val delegateSerializer = kotlinx.serialization.json.JsonObject.serializer()
	override val descriptor: SerialDescriptor
		get() = SerialDescriptor("FirnauhiModelRootPredicate", delegateSerializer.descriptor)

	override fun deserialize(decoder: Decoder): FirnauhiModelPredicate {
		val json = decoder.decodeSerializableValue(delegateSerializer).intoGson() as JsonObject
		return AndPredicate(CustomModelOverrideParser.parsePredicates(json).toTypedArray())
	}

	override fun serialize(encoder: Encoder, value: FirnauhiModelPredicate) {
		TODO("Cannot serialize firnauhi predicates")
	}
}
