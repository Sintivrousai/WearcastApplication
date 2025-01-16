package com.example.wearcast.data.upload


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UploadViewModel(
    private val wardrobeRepository: WardrobeRepository,
): ViewModel() {

    // User input fields
    var type: String = ""
    var name: String = ""
    var color: String = ""

    // StateFlow to store the last added item
    private val _lastAddedItem = MutableStateFlow<Wardrobe?>(null)
    val lastAddedItem = _lastAddedItem.asStateFlow()

    suspend fun getUserIdByUsername(username: String): Int? {
        return wardrobeRepository.getUserIdByUsername(username)
    }

    fun addTypedItemToWardrobe(userId: Int, type: String, name: String, color: String) {
        if (userId == -1) {
            println("Error: Invalid User ID")
            return
        }
        if (type.isNotBlank() && name.isNotBlank() && color.isNotBlank() ) {
            viewModelScope.launch {
                try {
                    wardrobeRepository.addToWardrobeByNameAndColor(userId, type, name, color) // Pass userId
                    val lastItem = wardrobeRepository.getLastAddedItem()
                    _lastAddedItem.value = lastItem
                } catch (e: Exception) {
                    println("Error: ${e.message}")
                }
            }
        } else {
            println("Please fill in all fields (type, name, and color).")
        }
    }
}