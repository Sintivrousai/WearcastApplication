package com.example.wearcast.data.upload

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WardrobeTableViewModelFactory(
    private val wardrobeRepository: WardrobeRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WardrobeTableViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WardrobeTableViewModel(wardrobeRepository) as T // Pass userId to the ViewModel
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
