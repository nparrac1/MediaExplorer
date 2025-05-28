package com.example.mediaexplorer.data

import com.example.mediaexplorer.model.Category

object DummyData {
    val categories = listOf(
        Category(
            id = "1",
            name = "Películas",
            description = "Colección de películas",
            imageUri = "android.resource://com.example.mediaexplorer/drawable/movies"
        ),
        Category(
            id = "2",
            name = "Series",
            description = "Colección de series",
            imageUri = "android.resource://com.example.mediaexplorer/drawable/series"
        ),
        Category(
            id = "3",
            name = "Anime",
            description = "Colección de anime",
            imageUri = "android.resource://com.example.mediaexplorer/drawable/anime"
        )
    )
}