package com.example.marvel.api

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.marvel.data.repository.CharacterRepository
import com.example.marvel.ui.model.CharacterUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterViewModelFactory(
    private val characterRepository: CharacterRepository,
    private val apiKeyViewModel: ApiKeyViewModel
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterViewModel::class.java)) {
            return CharacterViewModel(characterRepository, apiKeyViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}

class CharacterViewModel(
    private val characterRepository: CharacterRepository,
    private val apiKeyViewModel: ApiKeyViewModel
) : ViewModel() {
    private val _characters = MutableStateFlow<List<CharacterUI>>(emptyList())
    val characters: StateFlow<List<CharacterUI>> = _characters

    val characterImageUrl = MutableStateFlow<String?>(null)
    val errorMessage = MutableStateFlow<String?>(null)

    fun getApiKey(): String = apiKeyViewModel.apiKey
    fun getPrivateKey(): String = apiKeyViewModel.privateKey


    fun getCharacters(isNetworkAvailable: Boolean) {
        val apiKey = apiKeyViewModel.apiKey
        val privateKey = apiKeyViewModel.privateKey

        viewModelScope.launch {
            try {
                if (isNetworkAvailable) {
                    val ts = System.currentTimeMillis()
                    val hash = generateHash(privateKey, apiKey, ts)
                    characterRepository.refreshCharacters(apiKey, hash, ts)
                }

                characterRepository.getAllCharacters().collect { characters ->
                    _characters.value = characters
                }
            } catch (e: Exception) {
                errorMessage.value = "Ошибка: ${e.message}"
                Log.e("CharacterViewModel", "Ошибка загрузки персонажей: ${e.message}")
            }
        }
    }

    fun getCharacterImage(characterId: Int, apiKey: String, privateKey: String) {
        val ts = System.currentTimeMillis()
        val hash = generateHash(privateKey, apiKey, ts)
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.apiService.getCharacter(characterId, apiKey, hash, ts)

                if (response.isSuccessful && response.body() != null) {
                    val character = response.body()?.data?.results?.firstOrNull()
                    val imageUrl = character?.thumbnail?.path + "." + character?.thumbnail?.extension
                    characterImageUrl.value = imageUrl?.replace("http://", "https://")
                } else {
                    errorMessage.value = "Ошибка загрузки изображения персонажа"
                    Log.e("CharacterViewModel", "Ошибка ответа: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                errorMessage.value = "Ошибка: ${e.message}"
                Log.e("CharacterViewModel", "Ошибка получения изображения персонажа: ${e.message}")
            }
        }
    }
}
