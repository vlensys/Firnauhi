
package moe.nea.firnauhi.features.texturepack

import com.google.gson.JsonElement

interface FirnauhiModelPredicateParser {
    fun parse(jsonElement: JsonElement): FirnauhiModelPredicate?
}
