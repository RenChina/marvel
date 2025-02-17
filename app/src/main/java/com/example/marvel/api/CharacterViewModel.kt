package com.example.marvel.api

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterViewModelFactory(
    private val apiKeyViewModel: ApiKeyViewModel
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterViewModel::class.java)) {
            return CharacterViewModel(apiKeyViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}


class CharacterViewModel (val apiKeyViewModel: ApiKeyViewModel) : ViewModel() {
    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters = _characters.asStateFlow()

    val characterImageUrl = MutableStateFlow<String?>(null) // Новое свойство
    val errorMessage = MutableStateFlow<String?>(null)

    fun getCharacters(apiKey: String, privateKey: String) {
        val apiKey = apiKeyViewModel.apiKey
        val privateKey = apiKeyViewModel.privateKey
        viewModelScope.launch {
            try {
                val ts = System.currentTimeMillis()
                val hash = generateHash(privateKey, apiKey, ts)
                val response = RetrofitInstance.apiService.getCharacters(apiKey, hash, ts)

                if (response.isSuccessful) {
                    val marvelResponse = response.body()
                    if (marvelResponse != null) {
                        _characters.value = marvelResponse.data.results
                    } else {
                        errorMessage.value = "Не удалось получить данные."
                        Log.e("CharacterViewModel", "Ответ пуст")
                    }
                } else {
                    // Логирование ошибки и вывод текста
                    val errorBody = response.errorBody()?.string() ?: "Неизвестная ошибка"
                    errorMessage.value = "Ошибка загрузки персонажей: $errorBody"
                    Log.e("CharacterViewModel", "Ошибка ответа: $errorBody")
                }
            } catch (e: Exception) {
                errorMessage.value = "Ошибка: ${e.message}"
                Log.e("CharacterViewModel", "Ошибка получения данных: ${e.message}")
            }
        }
    }

    fun getCharacterImage(characterId: Int, apiKey: String, privateKey: String) {
        val apiKey = apiKeyViewModel.apiKey
        val privateKey = apiKeyViewModel.privateKey
        viewModelScope.launch {
            try {
                val ts = System.currentTimeMillis()
                val hash = generateHash(privateKey, apiKey, ts)
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