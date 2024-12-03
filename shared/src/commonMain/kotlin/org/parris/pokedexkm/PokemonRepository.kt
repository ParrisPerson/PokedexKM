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
import kotlinx.coroutines.flow.collectLatest


class PokemonRepository(httpClientProvider: HttpClientProvider) {
    private val client: HttpClient = httpClientProvider.createHttpClient()

    fun getPokemonList(gen: Int): Flow<List<Pokemon>> = flow {
        val response: GenerationResponse = client.get("https://pokeapi.co/api/v2/generation/$gen").body()
        val pokemonList = coroutineScope {
            response.pokemon_species.map { result ->
                async { fetchPokemonDetails(result.name) } // Llamadas paralelas
            }.awaitAll() // Espera a que todas terminen
        }
        emit(pokemonList)
    }

    fun getPokemonByGeneration(gen: Int): Flow<List<Pokemon>> = flow {
        val url = "https://pokeapi.co/api/v2/generation/$gen"
        val response: GenerationResponse = client.get(url).body()
        emit(response.pokemon_species.map {
            Pokemon(
                name = it.name.capitalize(),
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/showdown/${extractIdFromUrl(it.url)}.gif"
            )
        })
    }

    private fun extractIdFromUrl(url: String): String {
        return url.split("/").dropLast(1).last()
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
data class GenerationResponse(
    val pokemon_species: List<PokemonSpecies>
)

@Serializable
data class PokemonSpecies(
    val name: String,
    val url: String // URL del detalle del Pokémon
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
