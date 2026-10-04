package com.example.appcolegio.pantallas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.appcolegio.ui.theme.AzulColegio

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraAzul(titulo: String) {
    TopAppBar(
        title = { Text(titulo) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AzulColegio,
            titleContentColor = Color.White
        )
    )
}

/**
 * Item deslizable (swipe) con su propio SwipeToDismissBoxState.
 * Al deslizar >= 25% se pide confirmar la eliminacion.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDeslizable(
    onSolicitarEliminar: (SwipeToDismissBoxState) -> Unit,
    onClick: () -> Unit,
    contenido: @Composable ColumnScope.() -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        initialValue = SwipeToDismissBoxValue.Settled,
        positionalThreshold = { it * 0.25f }
    )

    LaunchedEffect(dismissState.currentValue) {
        if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart ||
            dismissState.currentValue == SwipeToDismissBoxValue.StartToEnd
        ) {
            onSolicitarEliminar(dismissState)
        }
    }

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            Card(
                modifier = Modifier.fillMaxSize(),
                colors = CardDefaults.cardColors(containerColor = Color.Red)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(20.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().clickable { onClick() }
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(12.dp), content = contenido)
        }
    }
}

@Composable
fun DialogoEliminar(
    mensaje: String,
    onConfirmar: () -> Unit,
    onCancelar: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancelar,
        title = { Text("Eliminar") },
        text = { Text(mensaje) },
        confirmButton = { TextButton(onClick = onConfirmar) { Text("Si, eliminar") } },
        dismissButton = { TextButton(onClick = onCancelar) { Text("Cancelar") } }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampoDropdown(
    etiqueta: String,
    opciones: List<String>,
    seleccionado: String,
    onSeleccion: (String) -> Unit
) {
    var expandido by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expandido,
        onExpandedChange = { expandido = !expandido }
    ) {
        OutlinedTextField(
            value = seleccionado,
            onValueChange = {},
            readOnly = true,
            label = { Text(etiqueta) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expandido, onDismissRequest = { expandido = false }) {
            opciones.forEach { op ->
                DropdownMenuItem(
                    text = { Text(op) },
                    onClick = { onSeleccion(op); expandido = false }
                )
            }
        }
    }
}
