package org.parris.pokedexkm

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.Serializable

class PokemonRepository(httpClientProvider: HttpClientProvider) {
    private val client: HttpClient = httpClientProvider.createHttpClient()

    fun getPokemonList(): Flow<List<Pokemon>> = flow {
        val response: PokemonListResponse = client.get("https://pokeapi.co/api/v2/pokemon?limit=150").body()
        emit(response.results.map { Pokemon(it.name) })
    }
}

@Serializable
data class PokemonListResponse(val results: List<PokemonResult>)

@Serializable
data class PokemonResult(val name: String)

data class Pokemon(val name: String)
