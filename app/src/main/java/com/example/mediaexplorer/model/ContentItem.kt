package com.example.mediaexplorer.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "contents",
       foreignKeys = [
           ForeignKey(entity = Category::class,
                      parentColumns = ["id"],
                      childColumns = ["categoriaId"],
                      onDelete = ForeignKey.CASCADE)
       ])
data class ContentItem(
    @PrimaryKey
    val id: String,
    val title: String,
    val tipo: String,
    val description: String,
    val imagen: String,
    val categoriaId: String
)