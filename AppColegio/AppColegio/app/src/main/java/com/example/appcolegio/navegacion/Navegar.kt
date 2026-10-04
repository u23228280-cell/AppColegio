package com.example.appcolegio.navegacion

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.WindowInsets
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.appcolegio.pantallas.*

private val rutasConBarra = listOf("inicio", "docentes", "cursos")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navegar() {
    val navController = rememberNavController()
    val rutaActual = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (rutaActual in rutasConBarra) {
                NavigationBar {
                    listOf("inicio" to "Inicio", "docentes" to "Docente", "cursos" to "Curso")
                        .forEach { (ruta, texto) ->
                            NavigationBarItem(
                                selected = rutaActual == ruta,
                                onClick = {
                                    navController.navigate(ruta) {
                                        popUpTo("inicio")
                                        launchSingleTop = true
                                    }
                                },
                                icon = {},
                                label = { Text(texto) },
                                alwaysShowLabel = true
                            )
                        }
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "inicio",
            modifier = Modifier.padding(padding)
        ) {
            composable("inicio") { InicioScreen() }
            composable("cursos") { ListCursoScreenUI(navController) }
            composable("docentes") { ListDocenteScreenUI(navController) }

            composable(
                "addCurso?id={id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType; defaultValue = -1 })
            ) { AddCursoScreenUI(navController, it.arguments?.getInt("id") ?: -1) }

            composable(
                "addDocente?id={id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType; defaultValue = -1 })
            ) { AddDocenteScreenUI(navController, it.arguments?.getInt("id") ?: -1) }

            composable(
                "detalleDocente/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { DetalleDocente(navController, it.arguments?.getInt("id") ?: -1) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InicioScreen() {
    Scaffold(topBar = { BarraAzul("Inicio") }) { padding ->
        Box(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("KVBE", style = MaterialTheme.typography.headlineLarge)
                Spacer(Modifier.height(8.dp))
                Text("Gestión de cursos y docentes")
            }
        }
    }
}
