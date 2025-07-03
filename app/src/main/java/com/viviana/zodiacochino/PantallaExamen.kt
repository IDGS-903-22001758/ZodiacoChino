package com.viviana.zodiacochino

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import java.io.File
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment

data class Pregunta(val texto: String, val opciones: List<String>, val correcta: Int)

val preguntas = listOf(
    Pregunta("¿Capital de Canadá?", listOf("Toronto", "Vancouver", "Ottawa", "Montreal"), 2),
    Pregunta("¿Cuál es el océano más grande?", listOf("Atlántico", "Índico", "Pacífico", "Ártico"), 2),
    Pregunta("¿Quién escribió 'Cien años de soledad'?", listOf("Borges", "Cervantes", "García Márquez", "Neruda"), 2),
    Pregunta("¿Elemento con símbolo 'O'?", listOf("Oro", "Osmio", "Oxígeno", "Plomo"), 2),
    Pregunta("¿Cuántos huesos tiene el cuerpo humano adulto?", listOf("206", "210", "250", "180"), 0),
    Pregunta("¿Cuál es el planeta más grande?", listOf("Tierra", "Saturno", "Júpiter", "Marte"), 2)
)


@Composable
fun PantallaExamen(navController: NavHostController) {
    val context = LocalContext.current
    val respuestas = remember { mutableStateListOf<Int?>().apply { repeat(preguntas.size) { add(null) } } }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        preguntas.forEachIndexed { index, pregunta ->
            Text("${index + 1}. ${pregunta.texto}", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            pregunta.opciones.forEachIndexed { i, opcion ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = respuestas[index] == i,
                        onClick = { respuestas[index] = i }
                    )
                    Text(opcion)
                }
            }
            Spacer(modifier = Modifier.height(16.dp)) // separación entre preguntas
        }

        Button(onClick = {
            val score = respuestas.mapIndexed { i, r -> if (r == preguntas[i].correcta) 1 else 0 }.sum()
            context.openFileOutput("resultado.txt", Context.MODE_PRIVATE).use {
                it.write(score.toString().toByteArray())
            }
            navController.navigate("resultado")
        }) {
            Text("Terminar")
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}
