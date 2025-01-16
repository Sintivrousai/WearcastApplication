package com.example.wearcast.OutfitCombinations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OutfitViewModel(
    private val outfitRepository: OutfitRepository
) : ViewModel() {

    val outfits: StateFlow<List<Outfit>> get() = outfitRepository.outfits

    fun loadOutfitsForUser(username: String) {
        viewModelScope.launch {
            outfitRepository.generateOutfits(username)
        }
    }
}