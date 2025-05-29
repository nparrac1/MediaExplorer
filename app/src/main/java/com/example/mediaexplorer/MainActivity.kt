package com.example.mediaexplorer

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.mediaexplorer.ui.theme.AppTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mediaexplorer.ui.theme.screens.*
import com.example.mediaexplorer.viewmodel.CategoryViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    private val requiredPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO
        )
    } else {
        arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.entries.all { it.value }
        if (!allGranted) {
            Toast.makeText(
                this,
                "Se requieren permisos para acceder a los archivos",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAndRequestPermissions()
        setContent {
            AppTheme {
                MainScreenContent()
            }
        }
    }

    private fun checkAndRequestPermissions() {
        val permissionsToRequest = requiredPermissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()

        if (permissionsToRequest.isNotEmpty()) {
            permissionLauncher.launch(permissionsToRequest)
        }
    }
}

@Composable
fun MainScreenContent() {
    val navController = rememberNavController()
    val viewModel: CategoryViewModel = viewModel()
    val categories = viewModel.categories.collectAsState().value
    val contents = viewModel.contents.collectAsState().value

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(
                onCategorySelected = { categoryId ->
                    navController.navigate("category/$categoryId")
                },
                onAddCategory = {
                    navController.navigate("add_category")
                },
                viewModel = viewModel
            )
        }
        composable("category/{categoryId}") { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: return@composable
            val category = categories.find { it.id == categoryId }
            category?.let {
                CategoryDetailScreen(
                    category = it,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() },
                    onContentSelected = { contentId ->
                        navController.navigate("content/$contentId")
                    }
                )
            }
        }
        composable("content/{contentId}") { backStackEntry ->
            val contentId = backStackEntry.arguments?.getString("contentId") ?: return@composable
            val content = contents.values.flatten().find { it.id == contentId }
            content?.let {
                ContentDetailScreen(
                    content = it,
                    viewModel = viewModel,
                    onDismiss = { navController.popBackStack() }
                )
            }
        }
        composable("add_category") {
            AddCategoryScreen(
                onSave = { category, imageUri ->
                    viewModel.addCategory(category, imageUri)
                    navController.popBackStack()
                },
                onCancel = {
                    navController.popBackStack()
                }
            )
        }
    }
}