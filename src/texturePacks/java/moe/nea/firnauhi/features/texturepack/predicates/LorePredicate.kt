
package moe.nea.firnauhi.features.texturepack.predicates

import com.google.gson.JsonElement
import moe.nea.firnauhi.features.texturepack.FirnauhiModelPredicate
import moe.nea.firnauhi.features.texturepack.FirnauhiModelPredicateParser
import moe.nea.firnauhi.features.texturepack.StringMatcher
import net.minecraft.world.item.ItemStack
import moe.nea.firnauhi.util.mc.loreAccordingToNbt

class LorePredicate(val matcher: StringMatcher) : FirnauhiModelPredicate {
    object Parser : FirnauhiModelPredicateParser {
        override fun parse(jsonElement: JsonElement): FirnauhiModelPredicate {
            return LorePredicate(StringMatcher.parse(jsonElement))
        }
    }

    override fun test(stack: ItemStack): Boolean {
        val lore = stack.loreAccordingToNbt
        return lore.any { matcher.matches(it) }
    }
}
