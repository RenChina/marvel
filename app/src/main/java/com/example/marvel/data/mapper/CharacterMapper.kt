package com.example.marvel.data.mapper

import com.example.marvel.api.Thumbnail
import com.example.marvel.data.db.CharacterEntity
import com.example.marvel.data.model.CharacterDTO
import com.example.marvel.data.model.ThumbnailDTO
import com.example.marvel.ui.model.CharacterUI

object CharacterMapper {
    fun fromApiToDto(apiModel: com.example.marvel.api.Character): CharacterDTO {
        return CharacterDTO(
            id = apiModel.id,
            name = apiModel.name,
            description = apiModel.description,
            imageUrl = "${apiModel.thumbnail.path}.${apiModel.thumbnail.extension}".replace("http://", "https://"),
            thumbnail = ThumbnailDTO(
                path = apiModel.thumbnail.path,
                extension = apiModel.thumbnail.extension
            )
        )
    }

    fun fromDtoToEntity(dto: CharacterDTO): CharacterEntity {
        return CharacterEntity(
            id = dto.id,
            name = dto.name,
            description = dto.description,
            imageUrl = dto.imageUrl,
            thumbnailPath = dto.thumbnail.path,
            thumbnailExtension = dto.thumbnail.extension
        )
    }

    fun fromEntityToUi(entity: CharacterEntity): CharacterUI {
        return CharacterUI(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            imageUrl = entity.imageUrl,
            thumbnail = Thumbnail(
                path = entity.thumbnailPath,
                extension = entity.thumbnailExtension
            )
        )
    }
}
