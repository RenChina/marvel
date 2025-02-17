package com.example.marvel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.marvel.api.ApiKeyViewModel
import com.example.marvel.api.CharacterViewModel
import com.example.marvel.api.CharacterViewModelFactory


class MainActivity : ComponentActivity() {

    private lateinit var apiKeyViewModel: ApiKeyViewModel
    private lateinit var characterViewModel: CharacterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        apiKeyViewModel = ViewModelProvider(this)[ApiKeyViewModel::class.java]

        val factory = CharacterViewModelFactory(apiKeyViewModel)
        characterViewModel = ViewModelProvider(this, factory)[CharacterViewModel::class.java]

        setContent {
            MarvelApp(characterViewModel)
        }
    }
}
