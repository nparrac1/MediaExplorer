package com.example.mediaexplorer.data.local.dao

import androidx.room.*
import com.example.mediaexplorer.model.ContentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContentDao {

    @Query("SELECT * FROM contents WHERE categoryId = :categoryId")
    fun getContentsByCategory(categoryId: Int): Flow<List<ContentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContent(content: ContentEntity)

    @Update
    suspend fun updateContent(content: ContentEntity)

    @Delete
    suspend fun deleteContent(content: ContentEntity)
}
