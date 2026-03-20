
package moe.nea.firnauhi.features.texturepack.predicates

import com.google.gson.JsonElement
import moe.nea.firnauhi.features.texturepack.FirnauhiModelPredicate
import moe.nea.firnauhi.features.texturepack.FirnauhiModelPredicateParser
import net.minecraft.world.item.ItemStack

object AlwaysPredicate : FirnauhiModelPredicate {
    override fun test(stack: ItemStack): Boolean {
        return true
    }

    object Parser : FirnauhiModelPredicateParser {
        override fun parse(jsonElement: JsonElement): FirnauhiModelPredicate {
            return AlwaysPredicate
        }
    }
}
