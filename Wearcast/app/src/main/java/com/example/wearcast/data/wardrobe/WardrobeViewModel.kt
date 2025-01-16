package com.example.wearcast.data.wardrobe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wearcast.data.Bottoms.Bottoms
import com.example.wearcast.data.Bottoms.BottomsRepository
import com.example.wearcast.data.Tops.Tops
import com.example.wearcast.data.Tops.TopsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
// Import Jackets and JacketsRepository
import com.example.wearcast.data.Jackets.Jackets
import com.example.wearcast.data.Jackets.JacketsRepository

class WardrobeViewModel(
    private val topsRepository: TopsRepository,
    private val bottomsRepository: BottomsRepository,
    private val jacketsRepository: JacketsRepository // Add jackets repository
) : ViewModel() {

    // Combine Tops, Bottoms, and Jackets into a single flow
    val wardrobeItems: Flow<Triple<List<Tops>, List<Bottoms>, List<Jackets>>> =
        combine(
            topsRepository.allTops,
            bottomsRepository.allBottoms,
            jacketsRepository.allJackets
        ) { tops, bottoms, jackets ->
            Triple(tops, bottoms, jackets)
        }

    fun insertJacket(jacket: Jackets) = viewModelScope.launch {
        jacketsRepository.insertJacket(jacket)
    }

    fun deleteAllJackets() = viewModelScope.launch {
        jacketsRepository.deleteAllJackets()
    }

    fun updateJacketFavoriteStatus(jacketId: Int, isFavorite: Boolean) = viewModelScope.launch {
        jacketsRepository.updateFavoriteStatus(jacketId, isFavorite)
    }

    fun insertTop(top: Tops) = viewModelScope.launch {
        topsRepository.insertTop(top)
    }

    fun insertBottom(bottom: Bottoms) = viewModelScope.launch {
        bottomsRepository.insertBottom(bottom)
    }

    fun deleteAllTops() = viewModelScope.launch {
        topsRepository.deleteAllTops()
    }

    fun deleteAllBottoms() = viewModelScope.launch {
        bottomsRepository.deleteAllBottoms()
    }

    fun updateTopFavoriteStatus(topId: Int, isFavorite: Boolean) = viewModelScope.launch {
        topsRepository.updateFavoriteStatus(topId, isFavorite)
    }

    fun updateBottomFavoriteStatus(bottomId: Int, isFavorite: Boolean) = viewModelScope.launch {
        bottomsRepository.updateFavoriteStatus(bottomId, isFavorite)
    }
}