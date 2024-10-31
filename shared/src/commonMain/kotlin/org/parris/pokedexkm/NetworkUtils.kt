package org.parris.pokedexkm

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object NetworkUtils {
    val httpClient = HttpClient() {
        install(ContentNegotiation) {
            json( json = Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            }, contentType = ContentType.Any)
        }
    }
}