package com.example.mediaexplorer.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CategoryRoomViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryRoomViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryRoomViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
