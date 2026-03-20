package moe.nea.firnauhi.repo

import io.github.moulberry.repo.data.NEURecipe

interface ExtraRecipeProvider {
	fun provideExtraRecipes(): Iterable<NEURecipe>
}
