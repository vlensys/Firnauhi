package moe.nea.firnauhi.gui.config

import com.google.auto.service.AutoService
import net.minecraft.client.gui.screens.Screen

@AutoService(FirnauhiConfigScreenProvider::class)
class BuiltInConfigScreenProvider : FirnauhiConfigScreenProvider {
    override val key: String
        get() = "builtin"

    override fun open(search: String?, parent: Screen?): Screen {
        return AllConfigsGui.makeBuiltInScreen(parent)
    }
}
