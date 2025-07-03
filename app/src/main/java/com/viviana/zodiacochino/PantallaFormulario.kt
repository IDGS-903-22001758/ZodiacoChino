package com.viviana.zodiacochino

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import java.io.File

@Composable
fun PantallaFormulario(navController: NavHostController) {
    val context = LocalContext.current
    var nombre by remember { mutableStateOf("") }
    var dia by remember { mutableStateOf("") }
    var mes by remember { mutableStateOf("") }
    var anio by remember { mutableStateOf("") }
    var sexo by remember { mutableStateOf("Masculino") }

    Column(Modifier.padding(16.dp)) {
        TextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre Completo") })
        Row {
            TextField(value = dia, onValueChange = { dia = it }, label = { Text("Día") }, modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
            Spacer(modifier = Modifier.width(8.dp))
            TextField(value = mes, onValueChange = { mes = it }, label = { Text("Mes") }, modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
            Spacer(modifier = Modifier.width(8.dp))
            TextField(value = anio, onValueChange = { anio = it }, label = { Text("Año") }, modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
        }
        Row {
            RadioButton(selected = sexo == "Masculino", onClick = { sexo = "Masculino" })
            Text("Masculino")
            Spacer(modifier = Modifier.width(8.dp))
            RadioButton(selected = sexo == "Femenino", onClick = { sexo = "Femenino" })
            Text("Femenino")
        }

        Row {
            Button(onClick = {
                nombre = ""; dia = ""; mes = ""; anio = ""; sexo = "Masculino"
            }) { Text("Limpiar") }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                guardarDatos(context, nombre, dia, mes, anio, sexo)
                navController.navigate("examen")
            }) {
                Text("Siguiente")
            }
        }
    }
}

fun guardarDatos(context: Context, nombre: String, dia: String, mes: String, anio: String, sexo: String) {
    val texto = "$nombre|$dia|$mes|$anio|$sexo"
    context.openFileOutput("usuario.txt", Context.MODE_PRIVATE).use {
        it.write(texto.toByteArray())
    }
}