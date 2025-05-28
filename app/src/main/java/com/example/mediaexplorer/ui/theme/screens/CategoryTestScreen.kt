package com.example.mediaexplorer.ui.theme.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.mediaexplorer.viewmodel.CategoryRoomViewModel
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items


@Composable
fun CategoryTestScreen(viewModel: CategoryRoomViewModel = viewModel())
 {
    val categories by viewModel.categories.collectAsState()

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {
        Text("Agregar Categoría", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                if (name.isNotBlank()) {
                    viewModel.addCategory(name, description)
                    name = ""
                    description = ""
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Guardar")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Divider()

        Text("Categorías Guardadas:", style = MaterialTheme.typography.titleMedium)
        LazyColumn {
            items(categories) { cat ->
                Text("- ${cat.name}: ${cat.description}")
            }
        }
    }
}
