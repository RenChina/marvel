package com.example.marvel.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApiService {
    @GET("characters/{characterId}")
    suspend fun getCharacter(
        @Path("characterId") characterId: Int,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("ts") ts: Long
    ): Response<MarvelCharacterResponse>
    @GET("characters")
    suspend fun getCharacters(
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("ts") ts: Long
    ): Response<MarvelCharacterResponse>
}

