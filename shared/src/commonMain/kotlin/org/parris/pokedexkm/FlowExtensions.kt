package org.parris.pokedexkm

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import kotlinx.coroutines.flow.flowOn

fun <T> Flow<T>.watch(scope: CoroutineScope, callback: (T) -> Unit) {
    scope.launch {
        this@watch.flowOn(Dispatchers.Main).collect { value ->
            callback(value)
        }
    }
}
