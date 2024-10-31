package org.parris.pokedexkm

import io.ktor.client.*

interface HttpClientProvider {
    fun createHttpClient(): HttpClient
}

expect class HttpClientProviderImpl() : HttpClientProvider
