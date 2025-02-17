package com.example.marvel

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.marvel.api.CharacterViewModel

@Composable
fun MarvelApp(characterViewModel: CharacterViewModel) {
    val navController = rememberNavController()
    NavGraph(
        navController = navController,
        characterViewModel = characterViewModel
    )
}