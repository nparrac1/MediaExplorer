package com.example.mediaexplorer.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.mediaexplorer.ui.theme.components.CategoryCard
import com.example.mediaexplorer.data.sampleCategories

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onCategorySelected: (String) -> Unit,
    onAddCategory: () -> Unit
) {


    Scaffold(
        topBar = {
            TopAppBar(title = { Text("MediaExplorer") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddCategory) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar categoría")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(sampleCategories) { category ->
                CategoryCard(category = category) {
                    onCategorySelected(category.id)
                }
            }
        }
    }
}