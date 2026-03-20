package moe.nea.firnauhi.features.texturepack.predicates

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.minecraft.world.entity.LivingEntity
import moe.nea.firnauhi.features.texturepack.CustomModelOverrideParser
import moe.nea.firnauhi.features.texturepack.FirnauhiModelPredicate
import moe.nea.firnauhi.features.texturepack.FirnauhiModelPredicateParser
import net.minecraft.world.item.ItemStack

class AndPredicate(val children: Array<FirnauhiModelPredicate>) : FirnauhiModelPredicate {
	override fun test(stack: ItemStack, holder: LivingEntity?): Boolean {
		return children.all { it.test(stack, holder) }
	}

    object Parser : FirnauhiModelPredicateParser {
        override fun parse(jsonElement: JsonElement): FirnauhiModelPredicate {
            val children =
                (jsonElement as JsonArray)
                    .flatMap {
	                    CustomModelOverrideParser.parsePredicates(it as JsonObject)
                    }
                    .toTypedArray()
            return AndPredicate(children)
        }

    }
}
