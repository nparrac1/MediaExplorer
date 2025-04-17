package com.example.mediaexplorer.model
import androidx.annotation.DrawableRes
data class Category(
    val id: String,
    val name: String,
    @DrawableRes val imageRes: Int
)