package com.example.mediaexplorer.ui.theme.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.mediaexplorer.R
import com.example.mediaexplorer.model.Category
import com.example.mediaexplorer.model.ContentItem
import com.example.mediaexplorer.viewmodel.CategoryViewModel
import com.example.mediaexplorer.ui.theme.components.ImagePicker
import java.util.UUID
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDetailScreen(
    category: Category,
    viewModel: CategoryViewModel,
    onBack: () -> Unit,
    onContentSelected: (String) -> Unit
) {
    var showAddContentDialog by remember { mutableStateOf(false) }
    var showEditCategoryDialog by remember { mutableStateOf(false) }
    var showDeleteCategoryConfirmation by remember { mutableStateOf(false) }
    var contentToDelete by remember { mutableStateOf<ContentItem?>(null) }
    var isEditMode by remember { mutableStateOf(false) }
    val contents = viewModel.contents.collectAsState().value[category.id] ?: emptyList()

    Box(modifier = Modifier.fillMaxSize()) {
        // Imagen de fondo
        Image(
            painter = rememberAsyncImagePainter(viewModel.getImageUri(category.imageUri)),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )

        // Gradiente oscuro sobre la imagen
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.3f),
                            Color.Black.copy(alpha = 0.7f)
                        )
                    )
                )
        )

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { 
                        Text(
                            text = category.name,
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back),
                                tint = Color.White
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { isEditMode = !isEditMode }) {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = stringResource(R.string.edit),
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = { showDeleteCategoryConfirmation = true }) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = stringResource(R.string.delete),
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showAddContentDialog = true },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = stringResource(R.string.add_content),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // Sección de imagen y descripción
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(viewModel.getImageUri(category.imageUri)),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 0.3f),
                                        Color.Black.copy(alpha = 0.7f)
                                    )
                                )
                            )
                    )

                    Text(
                        text = category.description,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    )
                }

                // Sección de contenidos con fondo oscuro
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.95f))
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(contents) { content ->
                            ContentItemCard(
                                content = content,
                                viewModel = viewModel,
                                onClick = { onContentSelected(content.id) },
                                isEditMode = isEditMode,
                                onDelete = { contentToDelete = content }
                            )
                        }
                    }
                }
            }
        }

        // Diálogos
        if (showAddContentDialog) {
            AddContentDialog(
                onDismiss = { showAddContentDialog = false },
                onSave = { newContent, imageUri ->
                    viewModel.addContentToCategory(newContent, imageUri)
                    showAddContentDialog = false
                },
                categoryId = category.id
            )
        }

        if (showEditCategoryDialog) {
            EditCategoryDialog(
                category = category,
                onDismiss = { showEditCategoryDialog = false },
                onSave = { updatedCategory, imageUri ->
                    viewModel.updateCategory(updatedCategory, imageUri)
                    showEditCategoryDialog = false
                }
            )
        }

        if (showDeleteCategoryConfirmation) {
            AlertDialog(
                onDismissRequest = { showDeleteCategoryConfirmation = false },
                title = { Text(stringResource(R.string.delete_category_confirmation)) },
                text = { Text(stringResource(R.string.delete_category_message)) },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.deleteCategory(category)
                            showDeleteCategoryConfirmation = false
                            onBack()
                        }
                    ) {
                        Text(stringResource(R.string.delete))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteCategoryConfirmation = false }) {
                        Text(stringResource(R.string.cancel))
                    }
                }
            )
        }

        contentToDelete?.let { content ->
            AlertDialog(
                onDismissRequest = { contentToDelete = null },
                title = { Text(stringResource(R.string.delete_content_confirmation)) },
                text = { Text(stringResource(R.string.delete_content_message)) },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.deleteContent(content)
                            contentToDelete = null
                        }
                    ) {
                        Text(stringResource(R.string.delete))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { contentToDelete = null }) {
                        Text(stringResource(R.string.cancel))
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContentItemCard(
    content: ContentItem,
    viewModel: CategoryViewModel,
    onClick: () -> Unit,
    isEditMode: Boolean,
    onDelete: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(viewModel.getImageUri(content.imagen)),
                contentDescription = content.title,
                modifier = Modifier
                    .size(80.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = content.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = content.tipo,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
            if (isEditMode) {
                IconButton(onClick = onDelete) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Eliminar contenido",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddContentDialog(
    onDismiss: () -> Unit,
    onSave: (ContentItem, Uri) -> Unit,
    categoryId: String
) {
    var title by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.add_content)) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(stringResource(R.string.title)) },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = tipo,
                    onValueChange = { tipo = it },
                    label = { Text(stringResource(R.string.tipo)) },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(stringResource(R.string.description)) },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                Text(
                    text = stringResource(R.string.imagen),
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
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank() && tipo.isNotBlank() && description.isNotBlank() && selectedImageUri != null) {
                        val newContent = ContentItem(
                            id = UUID.randomUUID().toString(),
                            title = title,
                            tipo = tipo,
                            description = description,
                            imagen = "", // Se actualizará en el ViewModel
                            categoriaId = categoryId // Asignamos el ID de la categoría actual
                        )
                        onSave(newContent, selectedImageUri!!)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditCategoryDialog(
    category: Category,
    onDismiss: () -> Unit,
    onSave: (Category, Uri?) -> Unit
) {
    var name by remember { mutableStateOf(category.name) }
    var description by remember { mutableStateOf(category.description) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.edit_category)) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
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
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val updatedCategory = category.copy(
                        name = name,
                        description = description
                    )
                    onSave(updatedCategory, selectedImageUri)
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
