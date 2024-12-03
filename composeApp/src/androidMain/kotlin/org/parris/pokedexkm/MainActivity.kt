package org.parris.pokedexkm

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter

import java.lang.reflect.Modifier

class MainActivity : ComponentActivity() {
    private val viewModel by lazy { PokemonViewModel(HttpClientProviderImpl()) }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App(viewModel)
         //   PokemonScreen(viewModel = viewModel)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {

}
