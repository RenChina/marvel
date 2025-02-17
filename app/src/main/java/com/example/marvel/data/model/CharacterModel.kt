package com.example.marvel.data.model

import com.example.marvel.data.db.CharacterEntity
import com.example.marvel.api.Character

data class CharacterUI(
    val id: Int,
    val name: String,
    val description: String?,
    val imageUrl: String
)
fun Character.toCharacterEntity(): CharacterEntity {
    return CharacterEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        imageUrl = "${this.thumbnail.path}.${this.thumbnail.extension}".replace("http://", "https://"),
        thumbnailPath = this.thumbnail.path,
        thumbnailExtension = this.thumbnail.extension
    )
}

fun CharacterEntity.toCharacterUI(): CharacterUI {
    return CharacterUI(
        id = this.id,
        name = this.name,
        description = this.description,
        imageUrl = this.imageUrl
    )
}
