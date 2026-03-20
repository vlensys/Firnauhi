
package moe.nea.firnauhi.util


fun runNull(block: () -> Unit): Nothing? {
    block()
    return null
}
