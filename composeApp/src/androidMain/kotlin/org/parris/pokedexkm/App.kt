package org.parris.pokedexkm

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest

@RequiresApi(Build.VERSION_CODES.P)
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = pokemon.name,
                            style = MaterialTheme.typography.body1
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(pokemon.imageUrl)
                                .decoderFactory(ImageDecoderDecoder.Factory())
                                .build(),
                            contentDescription = "${pokemon.name} GIF",
                            modifier = Modifier.size(150.dp)
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
@Preview
fun PreviewApp() {
    App(PokemonViewModel(HttpClientProviderImpl()))
}