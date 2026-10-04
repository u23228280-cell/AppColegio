package com.example.appcolegio.pantallas

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appcolegio.clases.Listas
import com.example.appcolegio.local.AppDatabase
import com.example.appcolegio.local.entidades.Curso
import kotlinx.coroutines.launch

/** cursoId = -1 -> registrar; cursoId >= 0 -> actualizar */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCursoScreenUI(navController: NavController, cursoId: Int) {
    val context = LocalContext.current
    val dao = remember { AppDatabase.getInstance(context).cursoDao() }
    val scope = rememberCoroutineScope()
    val esEdicion = cursoId >= 0

    var nombre by remember { mutableStateOf("") }
    var ciclo by remember { mutableStateOf(Listas.ciclos.first()) }
    var credito by remember { mutableStateOf("") }
    var carrera by remember { mutableStateOf(Listas.carreras.first()) }

    LaunchedEffect(cursoId) {
        if (esEdicion) {
            dao.buscar(cursoId)?.let {
                nombre = it.nombre
                ciclo = it.ciclo.toString()
                credito = it.credito.toString()
                carrera = it.carrera
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (esEdicion) "Actualizar Curso" else "Registrar Curso") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Ingresar nombre") },
                    modifier = Modifier.fillMaxWidth()
                )
                CampoDropdown("[Seleccione ciclo]", Listas.ciclos, ciclo) { ciclo = it }
                OutlinedTextField(
                    value = credito,
                    onValueChange = { credito = it },
                    label = { Text("Ingresar credito") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )
                CampoDropdown("[Seleccione carrera]", Listas.carreras, carrera) { carrera = it }
            }

            Button(
                onClick = {
                    val cred = credito.toDoubleOrNull()
                    if (nombre.isBlank() || cred == null) {
                        Toast.makeText(context, "Complete nombre y credito valido", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    scope.launch {
                        val curso = Curso(
                            id = if (esEdicion) cursoId else 0,
                            nombre = nombre.trim(),
                            ciclo = ciclo.toInt(),
                            credito = cred,
                            carrera = carrera
                        )
                        if (esEdicion) dao.actualizar(curso) else dao.insertar(curso)
                        Toast.makeText(
                            context,
                            if (esEdicion) "Curso actualizado" else "Curso registrado",
                            Toast.LENGTH_SHORT
                        ).show()
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (esEdicion) "Actualizar" else "Registrar")
            }
        }
    }
}
