package moe.nea.firnauhi.features.texturepack

import com.google.gson.JsonObject
import com.mojang.datafixers.util.Pair
import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import com.mojang.serialization.Decoder
import com.mojang.serialization.DynamicOps
import com.mojang.serialization.Encoder
import net.minecraft.client.renderer.item.ItemModels
import net.minecraft.world.item.ItemStack
import net.minecraft.resources.Identifier
import moe.nea.firnauhi.Firnauhi
import moe.nea.firnauhi.annotations.Subscribe
import moe.nea.firnauhi.events.FinalizeResourceManagerEvent
import moe.nea.firnauhi.features.texturepack.predicates.AndPredicate
import moe.nea.firnauhi.features.texturepack.predicates.CastPredicate
import moe.nea.firnauhi.features.texturepack.predicates.DisplayNamePredicate
import moe.nea.firnauhi.features.texturepack.predicates.ExtraAttributesPredicate
import moe.nea.firnauhi.features.texturepack.predicates.GenericComponentPredicate
import moe.nea.firnauhi.features.texturepack.predicates.ItemPredicate
import moe.nea.firnauhi.features.texturepack.predicates.LorePredicate
import moe.nea.firnauhi.features.texturepack.predicates.NotPredicate
import moe.nea.firnauhi.features.texturepack.predicates.OrPredicate
import moe.nea.firnauhi.features.texturepack.predicates.PetPredicate
import moe.nea.firnauhi.features.texturepack.predicates.PullingPredicate
import moe.nea.firnauhi.features.texturepack.predicates.SkullPredicate
import moe.nea.firnauhi.util.json.KJsonOps

object CustomModelOverrideParser {

	val LEGACY_CODEC: Codec<FirnauhiModelPredicate> =
		Codec.of(
			Encoder.error("cannot encode legacy firnauhi model predicates"),
			object : Decoder<FirnauhiModelPredicate> {
				override fun <T : Any?> decode(
					ops: DynamicOps<T>,
					input: T
				): DataResult<Pair<FirnauhiModelPredicate, T>> {
					try {
						val pred = Firnauhi.json.decodeFromJsonElement(
							FirnauhiRootPredicateSerializer,
							ops.convertTo(KJsonOps.INSTANCE, input))
						return DataResult.success(Pair.of(pred, ops.empty()))
					} catch (ex: Exception) {
						return DataResult.error { "Could not deserialize ${ex.message}" }
					}
				}
			}
		)

	val predicateParsers = mutableMapOf<Identifier, FirnauhiModelPredicateParser>()


	fun registerPredicateParser(name: String, parser: FirnauhiModelPredicateParser) {
		predicateParsers[Identifier.fromNamespaceAndPath("firnauhi", name)] = parser
	}

	init {
		registerPredicateParser("display_name", DisplayNamePredicate.Parser)
		registerPredicateParser("lore", LorePredicate.Parser)
		registerPredicateParser("all", AndPredicate.Parser)
		registerPredicateParser("any", OrPredicate.Parser)
		registerPredicateParser("not", NotPredicate.Parser)
		registerPredicateParser("item", ItemPredicate.Parser)
		registerPredicateParser("extra_attributes", ExtraAttributesPredicate.Parser)
		registerPredicateParser("pet", PetPredicate.Parser)
		registerPredicateParser("component", GenericComponentPredicate.Parser)
		registerPredicateParser("skull", SkullPredicate.Parser)
	}

	private val neverPredicate = listOf(
		object : FirnauhiModelPredicate {
			override fun test(stack: ItemStack): Boolean {
				return false
			}
		}
	)

	fun parsePredicates(predicates: JsonObject?): List<FirnauhiModelPredicate> {
		if (predicates == null) return neverPredicate
		val parsedPredicates = mutableListOf<FirnauhiModelPredicate>()
		for (predicateName in predicates.keySet()) {
			if (predicateName == "cast") { // 1.21.4
				parsedPredicates.add(CastPredicate.Parser.parse(predicates[predicateName]) ?: return neverPredicate)
			}
			if (predicateName == "pull") {
				parsedPredicates.add(PullingPredicate.Parser.parse(predicates[predicateName]) ?: return neverPredicate)
			}
			if (predicateName == "pulling") {
				parsedPredicates.add(PullingPredicate.AnyPulling)
			}
			if (!predicateName.startsWith("firnauhi:")) continue
			val identifier = Identifier.parse(predicateName)
			val parser = predicateParsers[identifier] ?: return neverPredicate
			val parsedPredicate = parser.parse(predicates[predicateName]) ?: return neverPredicate
			parsedPredicates.add(parsedPredicate)
		}
		return parsedPredicates
	}

	@JvmStatic
	fun parseCustomModelOverrides(jsonObject: JsonObject): Array<FirnauhiModelPredicate>? {
		val predicates = (jsonObject["predicate"] as? JsonObject) ?: return null
		val parsedPredicates = parsePredicates(predicates)
		if (parsedPredicates.isEmpty())
			return null
		return parsedPredicates.toTypedArray()
	}

	@Subscribe
	fun finalizeResources(event: FinalizeResourceManagerEvent) {
		ItemModels.ID_MAPPER.put(
			Firnauhi.identifier("predicates/legacy"),
			PredicateModel.Unbaked.CODEC
		)
		ItemModels.ID_MAPPER.put(
			Firnauhi.identifier("head_model"),
			HeadModelChooser.Unbaked.CODEC
		)
	}

}
