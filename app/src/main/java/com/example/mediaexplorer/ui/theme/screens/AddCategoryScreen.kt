package com.example.mediaexplorer.ui.theme.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.mediaexplorer.model.Category
import com.example.mediaexplorer.R
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.res.stringResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCategoryScreen(
    onSave: (Category) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current

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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(stringResource(R.string.name_category)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(
                    onClick = {
                        if (name.text.isBlank()) {
                            Toast.makeText(context, context.getString(R.string.name_error), Toast.LENGTH_SHORT).show()
                        } else {
                            val newCategory = Category(
                                id = System.currentTimeMillis().toString(),
                                name = name.text,
                                imageRes = R.drawable.default_category // imagen por defecto
                            )
                            onSave(newCategory)
                        }
                    }
                ) {
                    Text(stringResource(R.string.save))
                }
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedButton(onClick = onCancel) {
                    Text(stringResource(R.string.cancel))
                }
            }
        }
    }
}