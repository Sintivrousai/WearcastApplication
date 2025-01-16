package com.example.wearcast.data.upload

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WardrobeTableViewModel(val wardrobeRepository: WardrobeRepository) : ViewModel() {

    private val _wardrobeItems = MutableStateFlow<List<Wardrobe>>(emptyList())
    val wardrobeItems: StateFlow<List<Wardrobe>> = _wardrobeItems


    // Fetch wardrobe items for all users (optional; use carefully)
    fun fetchWardrobeItems() {
        viewModelScope.launch {
            try {
                _wardrobeItems.value = wardrobeRepository.getAllWardrobeItems()
            } catch (e: Exception) {
                e.printStackTrace() // Log the error
            }
        }
    }

    // Get user ID by username
    suspend fun getUserIdByUsername(username: String): Int? {
        return try {
            wardrobeRepository.getUserIdByUsername(username)
        } catch (e: Exception) {
            e.printStackTrace() // Log the error
            null
        }
    }

    // Fetch wardrobe items for a specific user
    fun fetchWardrobeItemsForUser(userId: Int) {
        viewModelScope.launch {
            try {
                println("Fetching wardrobe items for user ID: $userId") // Debugging
                val items = wardrobeRepository.getAllWardrobeItemsForUser(userId)
                println("Fetched items: $items") // Debugging
                _wardrobeItems.value = items
            } catch (e: Exception) {
                e.printStackTrace() // Log the error
            }
        }
    }

    // Remove an item from wardrobe
    fun removeWardrobeItem(item: Wardrobe) {
        viewModelScope.launch {
            try {
                wardrobeRepository.removeFromWardrobe(item)
                _wardrobeItems.value = _wardrobeItems.value.filter { it.id != item.id }
            } catch (e: Exception) {
                e.printStackTrace() // Log the error
            }
        }
    }

    // Update the favorite status of an item
    fun updateFavoriteStatus(wardrobeId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            try {
                // Update the UI state immediately
                _wardrobeItems.value = _wardrobeItems.value.map { item ->
                    if (item.id == wardrobeId) item.copy(isFavorite = isFavorite) else item
                }
                wardrobeRepository.updateFavoriteStatus(wardrobeId, isFavorite)
            } catch (e: Exception) {
                e.printStackTrace() // Log the error
            }
        }
    }
}
