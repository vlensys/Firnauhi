
package moe.nea.firnauhi.features.events.carnival

import moe.nea.firnauhi.util.data.Config
import moe.nea.firnauhi.util.data.ManagedConfig

object CarnivalFeatures {
	@Config
	object TConfig : ManagedConfig(identifier, Category.EVENTS) {
        val enableBombSolver by toggle("bombs-solver") { true }
        val displayTutorials by toggle("tutorials") { true }
    }

    val identifier: String
        get() = "carnival"
}
