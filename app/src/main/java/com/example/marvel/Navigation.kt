package com.example.marvel

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.marvel.api.CharacterViewModel
import com.example.marvel.ui.screens.CardInfo
import com.example.marvel.ui.screens.MainScreen


@Composable
fun NavGraph(
    navController: NavHostController,
    characterViewModel: CharacterViewModel
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            MainScreen(navController, characterViewModel)
        }
        composable("heroDetail/{url}/{name}/{description}") { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""
            val name = backStackEntry.arguments?.getString("name") ?: "Неизвестно"
            val description = backStackEntry.arguments?.getString("description") ?: "Описание отсутствует"

            CardInfo(
                url = url,
                name = name,
                description = description,
                onClose = { navController.popBackStack() }
            )
        }
    }
}

