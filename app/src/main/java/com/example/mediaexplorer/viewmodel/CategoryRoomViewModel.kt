package com.example.mediaexplorer.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediaexplorer.data.MediaExplorerDatabase
import com.example.mediaexplorer.model.CategoryEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoryRoomViewModel(application: Application) : AndroidViewModel(application) {

    // Se inicializa solo cuando se usa, para evitar errores de construcción
    private val categoryDao by lazy {
        MediaExplorerDatabase.getDatabase(application).categoryDao()
    }

    private val _categories = MutableStateFlow<List<CategoryEntity>>(emptyList())
    val categories = _categories.asStateFlow()

    init {
        Log.d("ROOM_VIEWMODEL", "ViewModel creado correctamente")
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            categoryDao.getAllCategories().collect { list ->
                _categories.value = list
            }
        }
    }

    fun addCategory(name: String, description: String) {
        viewModelScope.launch {
            val newCategory = CategoryEntity(name = name, description = description)
            categoryDao.insertCategory(newCategory)
        }
    }
}
