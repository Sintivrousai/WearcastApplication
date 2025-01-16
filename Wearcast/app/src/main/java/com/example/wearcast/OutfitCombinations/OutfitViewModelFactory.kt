package com.example.wearcast.OutfitCombinations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OutfitViewModelFactory(
    private val outfitRepository: OutfitRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OutfitViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OutfitViewModel(outfitRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}