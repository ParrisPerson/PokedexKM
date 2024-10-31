package org.parris.pokedexkm

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.shareIn

import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.launch

class PokemonViewModel(
    private val httpClientProvider: HttpClientProvider
) {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _pokemonList = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemonList = _pokemonList.asCommonFlow() // Convierte a CommonFlow

    init {
        fetchPokemonList()
    }

    private fun fetchPokemonList() {
        viewModelScope.launch {
            val repository = PokemonRepository(httpClientProvider)
            repository.getPokemonList().collect { pokemon ->
                _pokemonList.value = pokemon
            }
        }
    }

    fun onCleared() {
        viewModelScope.cancel()
    }
}
