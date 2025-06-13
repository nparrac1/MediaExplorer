package com.example.mediaexplorer.data.dao

import androidx.room.*
import com.example.mediaexplorer.model.ContentItem

@Dao
interface ContentDao {
    @Query("SELECT * FROM contents")
    suspend fun getAllContents(): List<ContentItem>

    @Query("SELECT * FROM contents WHERE categoriaId = :categoryId")
    suspend fun getContentsByCategory(categoryId: String): List<ContentItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContent(content: ContentItem)

    @Update
    suspend fun updateContent(content: ContentItem)

    @Delete
    suspend fun deleteContent(content: ContentItem)
} 