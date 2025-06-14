package com.example.mediaexplorer.data.remote

import com.example.mediaexplorer.model.Category
import com.example.mediaexplorer.model.ContentItem
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // Categorías
    @GET("categories")
    suspend fun getCategories(): List<Category>

    @POST("categories")
    suspend fun createCategory(@Body category: Category): Response<Category>

    @PUT("categories/{id}")
    suspend fun updateCategory(@Path("id") id: Int, @Body category: Category): Response<Category>

    @DELETE("categories/{id}")
    suspend fun deleteCategory(@Path("id") id: Int): Response<Unit>

    // Contenidos
    @GET("contents")
    suspend fun getContents(): List<ContentItem>

    @POST("contents")
    suspend fun createContent(@Body content: ContentItem): Response<ContentItem>

    @PUT("contents/{id}")
    suspend fun updateContent(@Path("id") id: Int, @Body content: ContentItem): Response<ContentItem>

    @DELETE("contents/{id}")
    suspend fun deleteContent(@Path("id") id: Int): Response<Unit>
}
