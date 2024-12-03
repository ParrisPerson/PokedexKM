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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PokemonViewModel(
    private val httpClientProvider: HttpClientProvider
) {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _pokemonList = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemonList: StateFlow<List<Pokemon>> get() = _pokemonList
    var isLoading: Boolean = true

    init {
       // fetchPokemonList(gen = 1)
    }

    fun fetchPokemonsByGeneration(gen: Int) {
        isLoading = true
        viewModelScope.launch {
            val repository = PokemonRepository(httpClientProvider)
            repository.getPokemonByGeneration(gen = gen).collectLatest { pokemon ->
                _pokemonList.value = pokemon
                isLoading = false
            }
        }
    }

    fun onCleared() {
        viewModelScope.cancel()
    }
}
