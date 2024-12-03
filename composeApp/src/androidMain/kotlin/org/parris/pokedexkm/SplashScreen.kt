package org.parris.pokedexkm

import RotatingImage
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SplashScreen(onNavigateToMain: () -> Unit) {
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(5000) // Espera 5 segundos
        onNavigateToMain() // Navega a la pantalla principal
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.secondary),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.marco),
                contentDescription = "Square Image",
                modifier = Modifier
                    .size(300.dp) // Tamaño cuadrado
                    .align(
                        Alignment.CenterHorizontally
                    )
                    .offset(y = (-120).dp) // Ajusta la posición para que quede encima
            )
            // Logo del Pokémon
            RotatingImage()

            Spacer(modifier = Modifier.height(16.dp))

            // Texto de bienvenida
            Text(
                text = "Bienvenido a la PokeDex\n de Marco!",
                color = Color.White,
                fontSize = 24.sp,
                style = MaterialTheme.typography.h4,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun RotatingImagePreview() {
    SplashScreen{

    }
}