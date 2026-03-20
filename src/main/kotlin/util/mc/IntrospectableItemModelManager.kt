package moe.nea.firnauhi.util.mc

import net.minecraft.resources.Identifier

interface IntrospectableItemModelManager {
	fun hasModel_firnauhi(identifier: Identifier): Boolean
}
