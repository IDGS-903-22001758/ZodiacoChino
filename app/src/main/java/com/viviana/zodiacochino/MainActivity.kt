package com.viviana.zodiacochino

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.viviana.zodiacochino.*

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "formulario") {
                    composable("formulario") { PantallaFormulario(navController) }
                    composable("examen") { PantallaExamen(navController) }
                    composable("resultado") { PantallaResultado(navController) }
                }
            }
        }
    }
}