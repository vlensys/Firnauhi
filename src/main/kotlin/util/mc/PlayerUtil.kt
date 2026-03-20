package moe.nea.firnauhi.util.mc

import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.player.Player


val Player.mainHandStack get() = this.getItemBySlot(EquipmentSlot.MAINHAND)
