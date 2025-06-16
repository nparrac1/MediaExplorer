package com.example.mediaexplorer.data.repository

import com.example.mediaexplorer.data.model.Category
import com.example.mediaexplorer.data.network.CategoryApiService
import com.example.mediaexplorer.data.network.RetrofitClient

class CategoryRepository {

    private val apiService: CategoryApiService = RetrofitClient.retrofit.create(CategoryApiService::class.java)

    suspend fun getCategories(): List<Category> {
        return apiService.getCategories()
    }

    suspend fun getCategoryById(id: String): Category {
        return apiService.getCategoryById(id.toInt()) // el ID es String en tu modelo
    }
}
