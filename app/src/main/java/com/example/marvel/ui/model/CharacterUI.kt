package com.example.marvel.ui.model

import com.example.marvel.api.Thumbnail

data class CharacterUI(
    val id: Int,
    val name: String,
    val description: String?,
    val imageUrl: String,
    val thumbnail: Thumbnail
)
