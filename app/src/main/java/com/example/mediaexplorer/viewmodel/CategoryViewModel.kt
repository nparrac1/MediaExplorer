package com.example.mediaexplorer.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import com.example.mediaexplorer.model.Category
import com.example.mediaexplorer.model.ContentItem
import com.example.mediaexplorer.R

class CategoryViewModel : ViewModel() {
    private val _categories = mutableStateListOf(
        Category("1", "Películas", R.drawable.peliculas),
        Category("2", "Series", R.drawable.series),
        Category("3", "Animes", R.drawable.anime)
    )
    val categories: List<Category> get() = _categories

    private val _contents = mutableStateMapOf<String, MutableList<ContentItem>>()
    val contents: Map<String, List<ContentItem>> get() = _contents

    fun addCategory(category: Category) {
        _categories.add(category)
    }

    fun getContentsForCategory(categoryId: String): List<ContentItem> {
        return _contents[categoryId] ?: emptyList()
    }

    fun addContentToCategory(categoryId: String, contentItem: ContentItem) {
        val list = _contents.getOrPut(categoryId) { mutableListOf() }
        list.add(contentItem)
    }
}
