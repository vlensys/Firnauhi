package moe.nea.firnauhi.compat.modmenu

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import moe.nea.firnauhi.gui.config.AllConfigsGui

class FirnauhiModMenuPlugin : ModMenuApi {
    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
        return ConfigScreenFactory { AllConfigsGui.makeScreen(parent = it) }
    }
}
