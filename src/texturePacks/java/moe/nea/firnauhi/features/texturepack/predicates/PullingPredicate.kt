package moe.nea.firnauhi.features.texturepack.predicates

import com.google.gson.JsonElement
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.BowItem
import net.minecraft.world.item.ItemStack
import moe.nea.firnauhi.features.texturepack.FirnauhiModelPredicate
import moe.nea.firnauhi.features.texturepack.FirnauhiModelPredicateParser

class PullingPredicate(val percentage: Double) : FirnauhiModelPredicate {
	companion object {
		val AnyPulling = PullingPredicate(0.1)
	}

	object Parser : FirnauhiModelPredicateParser {
		override fun parse(jsonElement: JsonElement): FirnauhiModelPredicate? {
			return PullingPredicate(jsonElement.asDouble)
		}
	}

	override fun test(stack: ItemStack, holder: LivingEntity?): Boolean {
		if (holder == null) return false
		return BowItem.getPowerForTime(holder.ticksUsingItem) >= percentage
	}

}
