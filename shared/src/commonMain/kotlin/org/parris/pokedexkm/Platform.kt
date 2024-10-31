package org.parris.pokedexkm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform