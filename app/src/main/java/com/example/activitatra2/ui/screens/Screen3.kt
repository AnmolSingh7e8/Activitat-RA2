package com.example.apilist.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.apilist.data.model.SettingsRepository
import kotlinx.coroutines.launch

@Composable
fun Screen3(settingsRepository: SettingsRepository) {
    val isDarkModeEnabled by settingsRepository.isDarkModeEnabled.collectAsState(initial = false)
    val selectedOption by settingsRepository.selectedOption.collectAsState(initial = "List")
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // Establezca el esquema de color según la preferencia del modo oscuro
    MaterialTheme(
        colorScheme = if (isDarkModeEnabled) darkColorScheme() else lightColorScheme()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(50.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título de la pantalla
            Text(
                "Settings",
                modifier = Modifier.padding(bottom = 100.dp),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            // Controles de configuración
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.width(200.dp)
            ) {
                // Interruptor para el modo oscuro
                Text(
                    "Dark Mode",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Switch(
                    checked = isDarkModeEnabled,
                    onCheckedChange = { settingsRepository.setDarkModeEnabled(it) }
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Selector para el modo de visualización de elementos
                Text(
                    "Item Display Mode",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(16.dp)
                        .background(color = MaterialTheme.colorScheme.outline),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = selectedOption,
                        modifier = Modifier
                            .clickable { expanded = true }
                            .padding(8.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .width(200.dp)
                            .heightIn(min = 100.dp, max = 200.dp)
                            .background(color = MaterialTheme.colorScheme.outline)
                    ) {
                        listOf("List", "Grid").forEach { option ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        option,
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                },
                                onClick = {
                                    settingsRepository.setSelectedOption(option)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
            // Botón para eliminar la base de datos
            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) {
                Text("Clear Database")
            }
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Confirm") },
                    text = { Text("Are you sure you want to delete all items?") },
                    confirmButton = {
                        Button(onClick = {
                            coroutineScope.launch {
                                settingsRepository.eliminarLista(context)
                            }
                            showDialog = false
                        }) {
                            Text("Yes")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showDialog = false }) {
                            Text("No")
                        }
                    }
                )
            }
        }
    }
}