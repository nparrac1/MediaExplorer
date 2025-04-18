package com.example.mediaexplorer.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mediaexplorer.ui.theme.components.CategoryCard
import com.example.mediaexplorer.viewmodel.CategoryViewModel
import com.example.mediaexplorer.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onCategorySelected: (String) -> Unit,
    onAddCategory: () -> Unit,
    viewModel: CategoryViewModel = viewModel()
) {
    val categories = viewModel.categories

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.app_name)) })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddCategory) {
                Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.add_category))
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(categories) { category ->
                CategoryCard(category = category) {
                    onCategorySelected(category.id)
                }
            }
        }
    }
}
