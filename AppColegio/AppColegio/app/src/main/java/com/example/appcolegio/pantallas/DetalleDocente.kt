package com.example.appcolegio.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appcolegio.local.AppDatabase
import com.example.appcolegio.local.entidades.Docente
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleDocente(navController: NavController, docenteId: Int) {
    val context = LocalContext.current
    val dao = remember { AppDatabase.getInstance(context).docenteDao() }
    val scope = rememberCoroutineScope()
    var docente by remember { mutableStateOf<Docente?>(null) }
    var confirmar by remember { mutableStateOf(false) }

    LaunchedEffect(docenteId) { docente = dao.buscar(docenteId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle Docente") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        docente?.let { d ->
            Column(
                modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Código: ${d.id}", fontWeight = FontWeight.Bold)
                Text("Nombres: ${d.nombre}")
                Text("Especialidad: ${d.especialidad}")
                Text("Correo: ${d.correo}")
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = { navController.navigate("addDocente?id=${d.id}") },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Editar") }
                OutlinedButton(
                    onClick = { confirmar = true },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Eliminar") }
            }
            if (confirmar) {
                DialogoEliminar(
                    mensaje = "¿Desea eliminar al docente ${d.nombre}?",
                    onConfirmar = {
                        confirmar = false
                        scope.launch {
                            dao.eliminar(d)
                            navController.popBackStack()
                        }
                    },
                    onCancelar = { confirmar = false }
                )
            }
        }
    }
}
