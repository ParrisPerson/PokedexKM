package org.parris.pokedexkm

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope



class PokemonRepository(httpClientProvider: HttpClientProvider) {
    private val client: HttpClient = httpClientProvider.createHttpClient()

    fun getPokemonList(): Flow<List<Pokemon>> = flow {
        val response: PokemonListResponse = client.get("https://pokeapi.co/api/v2/pokemon?limit=151").body()
        val pokemonList = coroutineScope {
            response.results.map { result ->
                async { fetchPokemonDetails(result.name) } // Llamadas paralelas
            }.awaitAll() // Espera a que todas terminen
        }
        emit(pokemonList)
    }

    private suspend fun fetchPokemonDetails(name: String): Pokemon {
        val details: PokemonDetailsResponse = client.get("https://pokeapi.co/api/v2/pokemon/$name").body()
        val gifUrl = details.sprites.other?.showdown?.frontDefault
            ?: "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/showdown/unknown.gif"
        return Pokemon(name = details.name, imageUrl = gifUrl)
    }
}

@Serializable
data class PokemonListResponse(val results: List<PokemonResult>)

@Serializable
data class PokemonResult(val name: String)

// Modelo para la respuesta de los detalles del Pokémon
@Serializable
data class PokemonDetailsResponse(
    val name: String,
    val sprites: PokemonSprites
)

@Serializable
data class PokemonSprites(
    val other: OtherSprites? = null
)

@Serializable
data class OtherSprites(
    val showdown: ShowdownSprites? = null
)

@Serializable
data class ShowdownSprites(
    @SerialName("front_default") val frontDefault: String? = null
)

data class Pokemon(
    val name: String,
    val imageUrl: String
)
