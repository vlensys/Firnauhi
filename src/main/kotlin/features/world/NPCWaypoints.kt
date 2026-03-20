package moe.nea.firnauhi.features.world

import moe.nea.firnauhi.annotations.Subscribe
import moe.nea.firnauhi.commands.thenExecute
import moe.nea.firnauhi.events.CommandEvent
import moe.nea.firnauhi.events.ReloadRegistrationEvent
import moe.nea.firnauhi.util.MoulConfigUtils
import moe.nea.firnauhi.util.ScreenUtil

object NPCWaypoints {

    var allNpcWaypoints = listOf<NavigableWaypoint>()

    @Subscribe
    fun onRepoReloadRegistration(event: ReloadRegistrationEvent) {
        event.repo.registerReloadListener {
            allNpcWaypoints = it.items.items.values
                .asSequence()
                .filter { !it.island.isNullOrBlank() }
                .map {
                    NavigableWaypoint.NPCWaypoint(it)
                }
                .toList()
        }
    }

    @Subscribe
    fun onOpenGui(event: CommandEvent.SubCommand) {
        event.subcommand("npcs") {
            thenExecute {
                ScreenUtil.setScreenLater(MoulConfigUtils.loadScreen(
                    "npc_waypoints",
                    NpcWaypointGui(allNpcWaypoints),
                    null))
            }
        }
    }


}
