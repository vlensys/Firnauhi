
package moe.nea.firnauhi.features.texturepack.predicates

import com.google.gson.JsonElement
import moe.nea.firnauhi.features.texturepack.FirnauhiModelPredicate
import moe.nea.firnauhi.features.texturepack.FirnauhiModelPredicateParser
import moe.nea.firnauhi.features.texturepack.StringMatcher
import net.minecraft.world.item.ItemStack
import moe.nea.firnauhi.util.mc.displayNameAccordingToNbt

data class DisplayNamePredicate(val stringMatcher: StringMatcher) : FirnauhiModelPredicate {
    override fun test(stack: ItemStack): Boolean {
        val display = stack.displayNameAccordingToNbt
        return stringMatcher.matches(display)
    }

    object Parser : FirnauhiModelPredicateParser {
        override fun parse(jsonElement: JsonElement): FirnauhiModelPredicate {
            return DisplayNamePredicate(StringMatcher.parse(jsonElement))
        }
    }
}
