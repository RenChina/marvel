package com.example.marvel

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.marvel.ui.screens.CardInfo
import com.example.marvel.data.AppUrls
import com.example.marvel.ui.screens.MainScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { MainScreen(navController) }
        composable("heroDetail/{heroUrl}") { backStackEntry ->
            val heroUrl = backStackEntry.arguments?.getString("heroUrl")
            val hero = AppUrls.heroes.find { it.imageUrl == heroUrl }
            hero?.let {
                CardInfo(
                    url = it.imageUrl,
                    nameResId = it.nameResId,
                    descriptionResId = it.descriptionResId,
                    onClose = { navController.popBackStack() }
                )
            }
        }
    }
}

