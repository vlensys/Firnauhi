

package moe.nea.firnauhi.util

import moe.nea.firnauhi.Firnauhi

object ClipboardUtils {
    fun setTextContent(string: String) {
        try {
            MC.keyboard.clipboard = string.ifEmpty { " " }
        } catch (e: Exception) {
            Firnauhi.logger.error("Could not write clipboard", e)
        }
    }

    fun getTextContents(): String {
        try {
            return MC.keyboard.clipboard ?: ""
        } catch (e: Exception) {
            Firnauhi.logger.error("Could not read clipboard", e)
            return ""
        }
    }
}
