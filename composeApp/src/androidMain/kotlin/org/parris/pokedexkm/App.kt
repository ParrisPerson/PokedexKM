package org.parris.pokedexkm

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun App(viewModel: PokemonViewModel) {
    MaterialTheme {
        var pokemonList by remember { mutableStateOf<List<Pokemon>>(emptyList()) }
        val coroutineScope = rememberCoroutineScope()

        // Obtener la lista de Pokémon cuando `App` se carga
        LaunchedEffect(Unit) {
            coroutineScope.launch {
                viewModel.pokemonList.collect { list ->
                    pokemonList = list
                }
            }
        }

        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Pokémon List", style = MaterialTheme.typography.h3)

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 16.dp)
            ) {
                items(pokemonList) { pokemon ->
                    Text(text = pokemon.name, style = MaterialTheme.typography.body1)
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewApp() {
    App(PokemonViewModel(HttpClientProviderImpl()))
}