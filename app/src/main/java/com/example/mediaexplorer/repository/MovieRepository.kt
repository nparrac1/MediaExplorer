package com.example.mediaexplorer.repository

import com.example.mediaexplorer.network.RetrofitClient
import com.example.mediaexplorer.network.MovieResponse

class MovieRepository {
    private val apiService = RetrofitClient.apiService

    suspend fun searchMovies(query: String, page: Int = 1): MovieResponse {
        return apiService.searchMovies(
            apiKey = "TU_API_KEY", // Reemplazar con tu API key real
            query = query,
            page = page
        )
    }

    suspend fun getPopularMovies(page: Int = 1): MovieResponse {
        return apiService.getPopularMovies(
            apiKey = "TU_API_KEY", // Reemplazar con tu API key real
            page = page
        )
    }
} 