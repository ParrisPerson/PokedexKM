import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import org.parris.pokedexkm.R

@Composable
fun RotatingImage() {
    // Define una animación infinita de rotación
    val infiniteTransition = rememberInfiniteTransition()

    // Configura el estado de rotación
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000, // Tiempo de rotación completa (2s)
                easing = LinearEasing // Movimiento constante
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    // Mostrar la imagen con la animación aplicada
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.pokemon_logo), // Cambia "your_image" por el nombre de tu imagen
            contentDescription = "Rotating Image",
            modifier = Modifier
                .size(100.dp)
                .graphicsLayer(rotationZ = rotationAngle)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RotatingImagePreview() {
    RotatingImage()
}
