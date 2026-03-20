
package moe.nea.firnauhi.features.texturepack.predicates

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import moe.nea.firnauhi.features.texturepack.CustomModelOverrideParser
import moe.nea.firnauhi.features.texturepack.FirnauhiModelPredicate
import moe.nea.firnauhi.features.texturepack.FirnauhiModelPredicateParser
import net.minecraft.world.item.ItemStack

class NotPredicate(val children: Array<FirnauhiModelPredicate>) : FirnauhiModelPredicate {
    override fun test(stack: ItemStack): Boolean {
        return children.none { it.test(stack) }
    }

    object Parser : FirnauhiModelPredicateParser {
        override fun parse(jsonElement: JsonElement): FirnauhiModelPredicate {
            return NotPredicate(CustomModelOverrideParser.parsePredicates(jsonElement as JsonObject).toTypedArray())
        }
    }
}
