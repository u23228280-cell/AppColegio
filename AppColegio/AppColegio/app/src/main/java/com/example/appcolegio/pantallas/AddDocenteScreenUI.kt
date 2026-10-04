package com.example.appcolegio.pantallas

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appcolegio.local.AppDatabase
import com.example.appcolegio.local.entidades.Docente
import kotlinx.coroutines.launch

/** docenteId = -1 -> registrar; >= 0 -> actualizar */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDocenteScreenUI(navController: NavController, docenteId: Int) {
    val context = LocalContext.current
    val dao = remember { AppDatabase.getInstance(context).docenteDao() }
    val scope = rememberCoroutineScope()
    val esEdicion = docenteId >= 0

    var nombre by remember { mutableStateOf("") }
    var especialidad by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }

    LaunchedEffect(docenteId) {
        if (esEdicion) {
            dao.buscar(docenteId)?.let {
                nombre = it.nombre
                especialidad = it.especialidad
                correo = it.correo
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (esEdicion) "Actualizar Docente" else "Registrar Docente") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = nombre, onValueChange = { nombre = it },
                    label = { Text("Ingresar nombre") }, modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = especialidad, onValueChange = { especialidad = it },
                    label = { Text("Ingresar especialidad") }, modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = correo, onValueChange = { correo = it },
                    label = { Text("Ingresar correo") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Button(
                onClick = {
                    if (nombre.isBlank() || especialidad.isBlank()) {
                        Toast.makeText(context, "Complete nombre y especialidad", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    scope.launch {
                        val d = Docente(
                            id = if (esEdicion) docenteId else 0,
                            nombre = nombre.trim(),
                            especialidad = especialidad.trim(),
                            correo = correo.trim()
                        )
                        if (esEdicion) dao.actualizar(d) else dao.insertar(d)
                        Toast.makeText(context, "Docente guardado", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text(if (esEdicion) "Actualizar" else "Registrar") }
        }
    }
}
