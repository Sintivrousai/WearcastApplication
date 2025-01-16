package com.example.wearcast.data.wardrobe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wearcast.data.Bottoms.BottomsRepository
import com.example.wearcast.data.Jackets.JacketsRepository
import com.example.wearcast.data.Tops.TopsRepository

class WardrobeViewModelFactory(
    private val topsRepository: TopsRepository,
    private val bottomsRepository: BottomsRepository,
    private val jacketsRepository: JacketsRepository // Add jackets repository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WardrobeViewModel::class.java)) {
            return WardrobeViewModel(topsRepository, bottomsRepository, jacketsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}