package com.example.mediaexplorer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mediaexplorer.model.CategoryEntity
import com.example.mediaexplorer.model.ContentEntity
import com.example.mediaexplorer.data.local.dao.CategoryDao
import com.example.mediaexplorer.data.local.dao.ContentDao

@Database(
    entities = [CategoryEntity::class, ContentEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MediaExplorerDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun contentDao(): ContentDao

    companion object {
        @Volatile
        private var INSTANCE: MediaExplorerDatabase? = null

        fun getDatabase(context: Context): MediaExplorerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MediaExplorerDatabase::class.java,
                    "media_explorer_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
