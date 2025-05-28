package com.example.mediaexplorer.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.mediaexplorer.ui.theme.screens.AddCategoryScreen
import com.example.mediaexplorer.ui.theme.screens.CategoryDetailScreen
import com.example.mediaexplorer.ui.theme.screens.ContentDetailScreen
import com.example.mediaexplorer.ui.theme.screens.MainScreen
import com.example.mediaexplorer.viewmodel.CategoryViewModel
import com.example.mediaexplorer.ui.theme.screens.CategoryTestScreen
import com.example.mediaexplorer.viewmodel.CategoryRoomViewModel
import android.app.Application
import androidx.compose.ui.platform.LocalContext
import com.example.mediaexplorer.viewmodel.CategoryRoomViewModelFactory





@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController()
) {
    val categoryViewModel: CategoryViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {

        composable("test_category") {
            val context = LocalContext.current
            val viewModel: CategoryRoomViewModel = viewModel(
                factory = CategoryRoomViewModelFactory(context.applicationContext as Application)
            )
            CategoryTestScreen(viewModel = viewModel)
        }

        // Pantalla principal
        composable("main") {
            MainScreen(
                viewModel = categoryViewModel,
                onCategorySelected = { categoryId ->
                    navController.navigate("category_detail/$categoryId")
                },
                onAddCategory = {
                    navController.navigate("add_category")
                }
            )
        }

        // Formulario para nueva categoría
        composable("add_category") {
            AddCategoryScreen(
                onSave = { newCategory ->
                    categoryViewModel.addCategory(newCategory)
                    navController.popBackStack()
                },
                onCancel = {
                    navController.popBackStack()
                }
            )
        }

        // Detalle de categoría con lista de contenidos
        composable(
            route = "category_detail/{categoryId}",
            arguments = listOf(navArgument("categoryId") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId")
            val category = categoryViewModel.categories.find { it.id == categoryId }

            if (category != null) {
                CategoryDetailScreen(
                    category = category,
                    viewModel = categoryViewModel,
                    onBack = { navController.popBackStack() },
                    onContentSelected = { contentId ->
                        navController.navigate("content_detail/$contentId")
                    }
                )
            }
        }

        // NUEVA: Pantalla de detalle de un contenido audiovisual
        composable(
            route = "content_detail/{contentId}",
            arguments = listOf(navArgument("contentId") { type = NavType.StringType })
        ) { backStackEntry ->
            val contentId = backStackEntry.arguments?.getString("contentId")
            val contentItem = categoryViewModel.contents
                .values.flatten()
                .find { it.id == contentId }

            if (contentItem != null) {
                ContentDetailScreen(
                    contentItem = contentItem,
                    onBack = { navController.popBackStack() }
                )
            }
        }

        composable("test_category") {
            val roomViewModel: CategoryRoomViewModel = viewModel()
            CategoryTestScreen(viewModel = roomViewModel)
        }
    }
}
