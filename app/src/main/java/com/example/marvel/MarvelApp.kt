package com.example.marvel

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MarvelApp() {
    val navController = rememberNavController()
    NavGraph(navController = navController)
}
