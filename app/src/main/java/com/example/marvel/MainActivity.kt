package com.example.marvel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.marvel.api.ApiKeyViewModel
import com.example.marvel.api.CharacterViewModel
import com.example.marvel.api.CharacterViewModelFactory
import com.example.marvel.api.RetrofitInstance
import com.example.marvel.data.db.MarvelDatabase
import com.example.marvel.data.repository.CharacterRepository

class MainActivity : ComponentActivity() {

    private lateinit var apiKeyViewModel: ApiKeyViewModel
    private lateinit var characterViewModel: CharacterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        apiKeyViewModel = ViewModelProvider(this)[ApiKeyViewModel::class.java]

        val marvelDatabase = MarvelDatabase.getInstance(this)
        val characterDao = marvelDatabase.characterDao()

        val characterRepository = CharacterRepository(
            apiService = RetrofitInstance.apiService,
            characterDao = characterDao
        )

        val factory = CharacterViewModelFactory(characterRepository, apiKeyViewModel)

        characterViewModel = ViewModelProvider(this, factory)[CharacterViewModel::class.java]

        setContent {
            MarvelApp(characterViewModel)
        }
    }
}
