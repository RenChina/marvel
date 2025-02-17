package com.example.marvel.data.repository

import com.example.marvel.api.MarvelApiService
import com.example.marvel.data.db.CharacterDao
import com.example.marvel.data.db.CharacterEntity
import com.example.marvel.data.mapper.CharacterMapper
import com.example.marvel.ui.model.CharacterUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharacterRepository(
    private val apiService: MarvelApiService,
    private val characterDao: CharacterDao
) {
    fun getAllCharacters(): Flow<List<CharacterUI>> {
        return characterDao.getAllCharacters().map { entities ->
            entities.map { CharacterMapper.fromEntityToUi(it) }
        }
    }

    suspend fun refreshCharacters(apiKey: String, hash: String, ts: Long) {
        val response = apiService.getCharacters(apiKey, hash, ts)
        if (response.isSuccessful) {
            response.body()?.data?.results?.let { characters ->
                val entities = characters.map {
                    CharacterMapper.fromDtoToEntity(CharacterMapper.fromApiToDto(it))
                }
                characterDao.insertCharacters(entities)
            }
        }
    }

    fun getCharacterById(id: Int): Flow<CharacterUI?> {
        return characterDao.getCharacterById(id).map { it?.let { entity ->
            CharacterMapper.fromEntityToUi(entity)
        } }
    }

    suspend fun fetchCharacterFromApi(characterId: Int, apiKey: String, hash: String, ts: Long) {
        val response = apiService.getCharacter(characterId, apiKey, hash, ts)
        if (response.isSuccessful) {
            val character = response.body()?.data?.results?.firstOrNull()
            if (character != null) {
                characterDao.insertCharacter(CharacterMapper.fromApiToDto(character).let {
                    CharacterMapper.fromDtoToEntity(it)
                })
            }
        } else {
            throw Exception("Failed to fetch character: ${response.errorBody()?.string()}")
        }
    }

    suspend fun saveCharacters(characters: List<CharacterEntity>) {
        characterDao.insertCharacters(characters)
    }
}
