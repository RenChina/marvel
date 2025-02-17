package com.example.marvel.data
import com.example.marvel.R


data class HeroData(
    val imageUrl: String,
    val nameResId: Int,
    val descriptionResId: Int
)

object AppUrls {
    const val MARVEL_LOGO_URL = "https://static.wikia.nocookie.net/marvelcinematicuniverse/images/f/fd/Normal_MarvelStudios2016Logo.png/revision/latest?cb=20180712111124&path-prefix=ru"

    val heroes = listOf(
        HeroData(
            imageUrl = "https://i.pinimg.com/564x/aa/0f/31/aa0f316bc2b6571a1b8c20d4ad6766a9.jpg",
            nameResId = R.string.hero_name_spider_man,
            descriptionResId = R.string.hero_description_spider_man
        ),
        HeroData(
            imageUrl = "https://i.pinimg.com/564x/64/bc/f8/64bcf8a9ebacf999b23f0248bfa5c69a.jpg",
            nameResId = R.string.hero_name_iron_man,
            descriptionResId = R.string.hero_description_iron_man
        ),
        HeroData(
            imageUrl = "https://i.pinimg.com/564x/6f/3a/a5/6f3aa5c8784e60563d787bceab7c8253.jpg",
            nameResId = R.string.hero_name_deadpool,
            descriptionResId = R.string.hero_description_deadpool
        )
    )
}
