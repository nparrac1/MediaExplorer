package com.example.mediaexplorer.ui.theme.screens

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mediaexplorer.R
import com.example.mediaexplorer.ui.theme.components.ImagePicker
import com.example.mediaexplorer.ui.theme.components.CancelButton
import com.example.mediaexplorer.ui.theme.components.SaveButton
import com.example.mediaexplorer.model.Category
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCategoryScreen(
    onSave: (Category, Uri?) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(R.string.new_category)) })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(stringResource(R.string.name_category)) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text(stringResource(R.string.description)) },
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = stringResource(R.string.category_cover),
                style = MaterialTheme.typography.titleMedium
            )
            
            ImagePicker(
                onImageSelected = { uri ->
                    selectedImageUri = uri
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CancelButton(
                    onClick = onCancel,
                    modifier = Modifier.weight(1f)
                )

                SaveButton(
                    name = name,
                    description = description,
                    selectedImageUri = selectedImageUri,
                    onSave = onSave,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}