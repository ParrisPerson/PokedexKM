package org.parris.pokedexkm

import io.ktor.utils.io.core.Closeable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

// Esta clase permite usar Flow en Swift con el método watch
class CommonFlow<T>(private val origin: Flow<T>) : Flow<T> by origin {
    fun watch(block: (T) -> Unit): Closeable {
        val job = onEach { block(it) }.launchIn(CoroutineScope(Dispatchers.Main))
        return object : Closeable {
            override fun close() {
                job.cancel()
            }
        }
    }
}

fun <T> Flow<T>.asCommonFlow() = CommonFlow(this)
