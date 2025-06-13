package com.example.mediaexplorer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mediaexplorer.data.dao.CategoryDao
import com.example.mediaexplorer.data.dao.ContentDao
import com.example.mediaexplorer.model.Category
import com.example.mediaexplorer.model.ContentItem

@Database(entities = [Category::class, ContentItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun contentDao(): ContentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "media_explorer_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
} 