package com.example.mediaexplorer.ui.theme.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import com.example.mediaexplorer.R
import com.example.mediaexplorer.model.ContentItem
import com.example.mediaexplorer.util.ImageUtils
import com.example.mediaexplorer.viewmodel.CategoryViewModel
import androidx.compose.foundation.background
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ContentDetailScreen(
    content: ContentItem,
    viewModel: CategoryViewModel,
    onDismiss: () -> Unit
) {
    var showEditDialog by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val isTablet = screenWidth >= 600.dp

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(if (isTablet) 0.8f else 0.9f),
            shape = MaterialTheme.shapes.large,
            color = Color.Transparent
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Imagen de fondo
                Image(
                    painter = rememberAsyncImagePainter(viewModel.getImageUri(content.imagen)),
                    contentDescription = content.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Gradiente oscuro para mejorar la legibilidad
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.3f),
                                    Color.Black.copy(alpha = 0.8f)
                                )
                            )
                        )
                )

                // Contenido
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Botones de acción
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(
                            onClick = { showEditDialog = true },
                            modifier = Modifier.background(
                                color = Color.White.copy(alpha = 0.2f),
                                shape = MaterialTheme.shapes.medium
                            )
                        ) {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = stringResource(R.string.edit),
                                tint = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier.background(
                                color = Color.White.copy(alpha = 0.2f),
                                shape = MaterialTheme.shapes.medium
                            )
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = stringResource(R.string.close),
                                tint = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Título
                    Text(
                        text = content.title,
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Tipo de contenido
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.2f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.tipo),
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = content.tipo,
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Descripción
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.2f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.description),
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = content.description,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }

    if (showEditDialog) {
        EditContentDialog(
            content = content,
            onDismiss = { showEditDialog = false },
            onSave = { updatedContent, imageUri ->
                viewModel.updateContent(
                    contentItem = updatedContent,
                    imageUri = imageUri
                )
                showEditDialog = false
            }
        )
    }
}

@Composable
fun EditContentDialog(
    content: ContentItem,
    onDismiss: () -> Unit,
    onSave: (ContentItem, Uri?) -> Unit
) {
    var title by remember { mutableStateOf(content.title) }
    var type by remember { mutableStateOf(content.tipo) }
    var description by remember { mutableStateOf(content.description) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var showTitleError by remember { mutableStateOf(false) }
    var showTypeError by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { selectedImageUri = it }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.edit_content)) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { 
                        title = it
                        showTitleError = false
                    },
                    label = { Text(stringResource(R.string.title)) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showTitleError,
                    supportingText = {
                        if (showTitleError) {
                            Text(stringResource(R.string.title_required))
                        }
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = type,
                    onValueChange = { 
                        type = it
                        showTypeError = false
                    },
                    label = { Text(stringResource(R.string.tipo)) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showTypeError,
                    supportingText = {
                        if (showTypeError) {
                            Text(stringResource(R.string.type_required))
                        }
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(stringResource(R.string.description)) },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { imagePickerLauncher.launch("image/*") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.imagen))
                }

                if (selectedImageUri != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Image(
                        painter = rememberAsyncImagePainter(selectedImageUri),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    var hasError = false
                    if (title.isBlank()) {
                        showTitleError = true
                        hasError = true
                    }
                    if (type.isBlank()) {
                        showTypeError = true
                        hasError = true
                    }
                    if (!hasError) {
                        val updatedContent = content.copy(
                            title = title,
                            tipo = type,
                            description = description
                        )
                        onSave(updatedContent, selectedImageUri)
                    }
                }
            ) {
                Text(stringResource(R.string.save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}
