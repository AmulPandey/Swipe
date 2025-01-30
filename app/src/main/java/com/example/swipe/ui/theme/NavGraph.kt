package com.example.swipe.ui.theme

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.swipe.ViewModel.ProductViewModel

@Composable
fun NavGraph(navController: NavHostController, viewModel: ProductViewModel) {
    NavHost(navController, startDestination = "productList") {
        composable("productList") { ProductListScreen(viewModel, navController) }
        composable("addProduct") {
            AddProductScreen(viewModel) {
                navController.popBackStack()
            }
        }
    }
}
