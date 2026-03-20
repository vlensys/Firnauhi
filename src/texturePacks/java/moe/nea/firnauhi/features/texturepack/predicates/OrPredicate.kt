
package moe.nea.firnauhi.features.texturepack.predicates

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import moe.nea.firnauhi.features.texturepack.CustomModelOverrideParser
import moe.nea.firnauhi.features.texturepack.FirnauhiModelPredicate
import moe.nea.firnauhi.features.texturepack.FirnauhiModelPredicateParser
import net.minecraft.world.item.ItemStack

class OrPredicate(val children: Array<FirnauhiModelPredicate>) : FirnauhiModelPredicate {
    override fun test(stack: ItemStack): Boolean {
        return children.any { it.test(stack) }
    }

    object Parser : FirnauhiModelPredicateParser {
        override fun parse(jsonElement: JsonElement): FirnauhiModelPredicate {
            val children =
                (jsonElement as JsonArray)
                    .flatMap {
	                    CustomModelOverrideParser.parsePredicates(it as JsonObject)
                    }
                    .toTypedArray()
            return OrPredicate(children)
        }

    }
}
