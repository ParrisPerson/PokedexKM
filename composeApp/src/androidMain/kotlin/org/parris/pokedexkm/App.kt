package org.parris.pokedexkm

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import okhttp3.internal.wait

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun App(viewModel: PokemonViewModel) {
    var showSplash by remember { mutableStateOf(true) }


    val tabOptions = listOf("Gen 1", "Gen 2", "Gen 3", "Gen 4", "Gen 5", "Gen 6", "Gen 7", "Gen 8", "Gen 9")
    var selectedTabIndex by remember { mutableStateOf(0) } // Índice de la tab seleccionada
    var isLoading by remember { mutableStateOf(true) }
    // Observa el estado de la lista de Pokémon en el ViewModel
    val pokemonList by viewModel.pokemonList.collectAsState(initial = emptyList())

    LaunchedEffect(selectedTabIndex) {
        viewModel.fetchPokemonsByGeneration(selectedTabIndex + 1)
        viewModel.isLoading = false
    }



    if (showSplash) {
        SplashScreen {
            showSplash = false
        }
    } else {
        MaterialTheme {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.secondary)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Pokémon List", style = MaterialTheme.typography.h3)

                // Componente de las Tabs
                ScrollableTabRow(
                    selectedTabIndex = selectedTabIndex,
                    edgePadding = 16.dp, // Espaciado al inicio y al final
                    backgroundColor = MaterialTheme.colors.secondary
                ) {
                    tabOptions.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = { Text(title) }
                        )
                    }
                }

                // Contenido de la lista de Pokémon
                if (viewModel.isLoading) {
                    // Muestra mensaje de "Cargando..." mientras la lista está vacía
                    Text("Loading...", style = MaterialTheme.typography.h6, modifier = Modifier.padding(16.dp))
                } else {


                    //DrawPoke(viewModel = viewModel, gen = selectedTabIndex + 1)
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
    }


}

@Composable
fun DrawPoke(  viewModel: PokemonViewModel, gen: Int) {
   // var pokemonList by remember { mutableStateOf<List<Pokemon>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(true) }
    val pokemonList by viewModel.pokemonList.collectAsState(initial = emptyList())
    // Obtener la lista de Pokémon cuando `App` se carga

    LaunchedEffect(gen) {
        viewModel.fetchPokemonsByGeneration(gen = gen)
        isLoading = false
    }

    if (isLoading) {
        Text(
            text = "Loading...",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(16.dp)
        )
    }
    else {
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

@RequiresApi(Build.VERSION_CODES.P)
@Composable
@Preview
fun PreviewApp() {
    App(PokemonViewModel(HttpClientProviderImpl()))
}