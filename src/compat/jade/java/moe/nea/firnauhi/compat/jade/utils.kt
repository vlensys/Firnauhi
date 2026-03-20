package moe.nea.firnauhi.compat.jade

import moe.nea.firnauhi.util.SBData

fun isOnMiningIsland(): Boolean =
	SBData.skyblockLocation?.hasCustomMining ?: false
