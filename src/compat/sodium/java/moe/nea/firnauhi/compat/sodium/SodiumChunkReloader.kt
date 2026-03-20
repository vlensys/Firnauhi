package moe.nea.firnauhi.compat.sodium

import moe.nea.firnauhi.mixins.sodium.accessor.AccessorSodiumWorldRenderer
import net.caffeinemc.mods.sodium.client.render.SodiumWorldRenderer

class SodiumChunkReloader : Runnable {
    override fun run() {
        (SodiumWorldRenderer.instanceNullable() as? AccessorSodiumWorldRenderer)
            ?.renderSectionManager_firnauhi
            ?.markGraphDirty()
    }
}
