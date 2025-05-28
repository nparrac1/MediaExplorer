package com.example.mediaexplorer.ui.theme.components

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.mediaexplorer.R
import com.example.mediaexplorer.model.Category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CancelButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(stringResource(R.string.cancel))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveButton(
    name: String,
    description: String,
    selectedImageUri: Uri?,
    onSave: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val nameError = stringResource(R.string.name_error)
    val imageError = stringResource(R.string.image_required)
    
    Button(
        onClick = {
            if (name.isBlank()) {
                Toast.makeText(context, nameError, Toast.LENGTH_SHORT).show()
                return@Button
            }
            if (selectedImageUri == null) {
                Toast.makeText(context, imageError, Toast.LENGTH_SHORT).show()
                return@Button
            }
            onSave(
                Category(
                    id = java.util.UUID.randomUUID().toString(),
                    name = name,
                    description = description,
                    imageUri = selectedImageUri.toString()
                )
            )
        },
        modifier = modifier
    ) {
        Text(stringResource(R.string.save))
    }
} 