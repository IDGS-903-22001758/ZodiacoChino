package com.viviana.zodiacochino

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.viviana.zodiacochino.R
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PantallaResultado(navController: NavHostController) {
    val context = LocalContext.current
    var resultadoTexto by remember { mutableStateOf("") }
    var signo by remember { mutableStateOf("") }
    var imagenZodiacoId by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(Unit) {
        val usuario = context.openFileInput("usuario.txt").bufferedReader().readText().split("|")
        val nombre = usuario[0]
        val dia = usuario[1].toInt()
        val mes = usuario[2].toInt()
        val anio = usuario[3].toInt()

        val edad = calcularEdad(anio, mes, dia)
        signo = calcularSignoChino(anio)

        val calificacion = context.openFileInput("resultado.txt").bufferedReader().readText()

        resultadoTexto = "Hola $nombre\nTienes $edad años y tu signo zodiacal chino es $signo\nCalificación: $calificacion"

        imagenZodiacoId = when (signo.lowercase()) {
            "rata" -> R.drawable.rata
            "buey" -> R.drawable.buey
            "tigre" -> R.drawable.tigre
            "conejo" -> R.drawable.conejo
            "dragón", "dragon" -> R.drawable.dragon
            "serpiente" -> R.drawable.serpiente
            "caballo" -> R.drawable.caballo
            "cabra" -> R.drawable.cabra
            "mono" -> R.drawable.mono
            "gallo" -> R.drawable.gallo
            "perro" -> R.drawable.perro
            "cerdo" -> R.drawable.cerdo
            else -> null
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(resultadoTexto)
        Spacer(modifier = Modifier.height(16.dp))
        imagenZodiacoId?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = "Signo Chino",
                modifier = Modifier.size(200.dp)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun calcularEdad(anio: Int, mes: Int, dia: Int): Int {
    val today = LocalDate.now()
    val birthDate = LocalDate.of(anio, mes, dia)
    return today.year - birthDate.year - if (today.dayOfYear < birthDate.dayOfYear) 1 else 0
}

fun calcularSignoChino(anio: Int): String {
    val signos = listOf(
        "Rata", "Buey", "Tigre", "Conejo", "Dragón", "Serpiente",
        "Caballo", "Cabra", "Mono", "Gallo", "Perro", "Cerdo"
    )
    return signos[(anio - 1900) % 12]
}