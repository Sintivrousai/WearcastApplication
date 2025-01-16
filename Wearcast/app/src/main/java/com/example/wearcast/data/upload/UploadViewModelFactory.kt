package com.example.wearcast.data.upload

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wearcast.data.upload.WardrobeRepository

class UploadViewModelFactory(
    private val wardrobeRepository: WardrobeRepository,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UploadViewModel::class.java)) {
            return UploadViewModel(wardrobeRepository) as T // Pass user_id to ViewModel
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

