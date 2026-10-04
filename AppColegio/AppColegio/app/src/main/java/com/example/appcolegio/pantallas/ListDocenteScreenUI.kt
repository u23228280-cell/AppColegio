package com.example.appcolegio.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appcolegio.local.AppDatabase
import com.example.appcolegio.local.entidades.Docente
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListDocenteScreenUI(navController: NavController) {
    val context = LocalContext.current
    val dao = remember { AppDatabase.getInstance(context).docenteDao() }
    val lista by dao.listar().collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

    var docenteEliminar by remember { mutableStateOf<Docente?>(null) }
    var mostrarDialogo by remember { mutableStateOf(false) }
    var dismissActual by remember { mutableStateOf<SwipeToDismissBoxState?>(null) }

    Scaffold(
        topBar = { BarraAzul("Docente") },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("addDocente") }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(lista, key = { it.id }) { bean ->
                ItemDeslizable(
                    onSolicitarEliminar = { estado ->
                        docenteEliminar = bean
                        mostrarDialogo = true
                        dismissActual = estado
                    },
                    onClick = { navController.navigate("detalleDocente/${bean.id}") }
                ) {
                    Text("Código : ${bean.id}", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Text("Nombres : ${bean.nombre}", fontSize = 13.sp)
                    Text("Especialidad : ${bean.especialidad}", fontSize = 12.sp)
                }
            }
        }
    }

    if (mostrarDialogo && docenteEliminar != null) {
        DialogoEliminar(
            mensaje = "¿Desea eliminar al docente ${docenteEliminar!!.nombre}?",
            onConfirmar = {
                val d = docenteEliminar!!
                mostrarDialogo = false
                docenteEliminar = null
                scope.launch { dao.eliminar(d) }
            },
            onCancelar = {
                mostrarDialogo = false
                docenteEliminar = null
                scope.launch { dismissActual?.reset() }
            }
        )
    }
}
