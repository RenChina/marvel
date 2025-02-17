package com.example.marvel.data.model

data class ThumbnailDTO(
    val path: String,
    val extension: String
)

data class CharacterDTO(
    val id: Int,
    val name: String,
    val description: String?,
    val imageUrl: String,
    val thumbnail: ThumbnailDTO
)
