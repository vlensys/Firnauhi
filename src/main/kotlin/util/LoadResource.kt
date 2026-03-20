
package moe.nea.firnauhi.util

import java.io.InputStream
import kotlin.io.path.inputStream
import kotlin.jvm.optionals.getOrNull
import net.minecraft.resources.Identifier
import moe.nea.firnauhi.repo.RepoDownloadManager


fun Identifier.openFirnauhiResource(): InputStream {
    val resource = MC.resourceManager.getResource(this).getOrNull()
    if (resource == null) {
        if (namespace == "neurepo")
            return RepoDownloadManager.repoSavedLocation.resolve(path).inputStream()
        error("Could not read resource $this")
    }
    return resource.open()
}

