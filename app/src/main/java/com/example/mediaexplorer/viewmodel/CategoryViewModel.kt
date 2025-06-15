package com.example.mediaexplorer.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediaexplorer.data.AppDatabase
import com.example.mediaexplorer.data.DummyData
import com.example.mediaexplorer.model.Category
import com.example.mediaexplorer.model.ContentItem
import com.example.mediaexplorer.util.ImageUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val categoryDao = database.categoryDao()
    private val contentDao = database.contentDao()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    private val _contents = MutableStateFlow<Map<String, List<ContentItem>>>(emptyMap())
    val contents: StateFlow<Map<String, List<ContentItem>>> = _contents

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            var categoriesList = categoryDao.getAllCategories()
            
            // Si no hay categorías, cargar las categorías de ejemplo
            if (categoriesList.isEmpty()) {
                DummyData.categories.forEach { category ->
                    categoryDao.insertCategory(category)
                }
                categoriesList = categoryDao.getAllCategories()
            }
            
            _categories.value = categoriesList
            loadContentsForCategories(categoriesList)
        }
    }

    private fun loadContentsForCategories(categoriesList: List<Category>) {
        viewModelScope.launch {
            val contentsMap = mutableMapOf<String, List<ContentItem>>()
            for (category in categoriesList) {
                val contentsForCategory = contentDao.getContentsByCategory(category.id)
                contentsMap[category.id] = contentsForCategory
            }
            _contents.value = contentsMap
        }
    }

    fun addCategory(category: Category, imageUri: Uri?) {
        viewModelScope.launch {
            val imagePath = imageUri?.let {
                ImageUtils.saveImageToInternalStorage(getApplication(), it)
            } ?: category.imageUri
            val newCategory = category.copy(imageUri = imagePath)
            categoryDao.insertCategory(newCategory)
            loadCategories()
        }
    }

    fun updateCategory(category: Category, imageUri: Uri? = null) {
        viewModelScope.launch {
            val imagePath = imageUri?.let { 
                ImageUtils.saveImageToInternalStorage(getApplication(), it)
            } ?: category.imageUri
            val updatedCategory = category.copy(imageUri = imagePath)
            categoryDao.updateCategory(updatedCategory)
            loadCategories()
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            categoryDao.deleteCategory(category)
            loadCategories()
        }
    }

    fun addContentToCategory(contentItem: ContentItem, imageUri: Uri?) {
        viewModelScope.launch {
            try {
                val imagePath = imageUri?.let {
                    ImageUtils.saveImageToInternalStorage(getApplication(), it)
                } ?: contentItem.imagen
                val newContent = contentItem.copy(imagen = imagePath)
                contentDao.insertContent(newContent)
                loadContentsForCategories(_categories.value)
            } catch (e: Exception) {
                // Si hay un error al guardar la imagen, guardamos el contenido sin imagen
                val newContent = contentItem.copy(imagen = "")
                contentDao.insertContent(newContent)
                loadContentsForCategories(_categories.value)
            }
        }
    }

    fun updateContent(contentItem: ContentItem, imageUri: Uri? = null) {
        viewModelScope.launch {
            val imagePath = imageUri?.let { 
                ImageUtils.saveImageToInternalStorage(getApplication(), it)
            } ?: contentItem.imagen
            val updatedContent = contentItem.copy(imagen = imagePath)
            contentDao.updateContent(updatedContent)
            loadContentsForCategories(_categories.value)
        }
    }

    fun deleteContent(contentItem: ContentItem) {
        viewModelScope.launch {
            contentDao.deleteContent(contentItem)
            loadContentsForCategories(_categories.value)
        }
    }

    fun getImageUri(imagePath: String): Uri {
        return if (ImageUtils.isResourceUri(imagePath)) {
            Uri.parse(imagePath)
        } else {
            ImageUtils.getImageUriFromPath(getApplication(), imagePath)
        }
    }
}
