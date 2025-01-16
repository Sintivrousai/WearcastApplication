package com.example.wearcast.data.Bottoms

import com.example.wearcast.data.Tops.Tops
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BottomsRepository(private val bottomsDao: BottomsDao) {

    val colors = listOf(
        "black", "white", "brown", "grey", "beige", "red", "orange", "pink",
        "purple", "blue", "light_lue", "green", "yellow", "bordeaux"
    )



    private val bottomsList: List<Bottoms> by lazy {
        colors.flatMap { color ->
            listOf(
                Bottoms(name = "Shorts", color = color, imageRes = "shorts_${color}_1", isForWomen = true, isFavorite = false, points = 1),
                Bottoms(name = "Mini Skirt", color = color, imageRes = "mini_skirt_${color}", isForWomen = true, isFavorite = false, points = 1),
                Bottoms(name = "Leggings", color = color, imageRes = "leggings_${color}", isForWomen = true, isFavorite = false, points = 2),
                Bottoms(name = "Long skirt", color = color, imageRes = "long_skirt_${color}", isForWomen = true, isFavorite = false, points = 2),
                Bottoms(name = "Trousers", color = color, imageRes = "trousers_${color}_1", isForWomen = true, isFavorite = false, points = 3),
                Bottoms(name = "Jeans", color = color, imageRes = "jeans_${color}_1", isForWomen = true, isFavorite = false, points = 4),
                Bottoms(name = "Sweatpants", color = color, imageRes = "sweatpants_${color}", isForWomen = true, isFavorite = false, points = 4),

                Bottoms(name = "Shorts", color = color, imageRes = "shorts_${color}_0", isForWomen = false, isFavorite = false, points = 1),
                Bottoms(name = "Trousers", color = color, imageRes = "trousers_${color}_0", isForWomen = false, isFavorite = false, points = 3),
                Bottoms(name = "Jeans", color = color, imageRes = "jeans_${color}_0", isForWomen = false, isFavorite = false, points = 4),
                Bottoms(name = "Sweatpants", color = color, imageRes = "sweatpants_${color}", isForWomen = false, isFavorite = false, points = 5),
            )
        }
    }

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val _allBottoms: MutableStateFlow<List<Bottoms>> = MutableStateFlow(emptyList())
    val allBottoms: StateFlow<List<Bottoms>> = _allBottoms

    init {
        // Insert predefined tops if the database is empty
        coroutineScope.launch {
            val currentBottoms = bottomsDao.getAllBottoms().first()  // Get the current list from the database
            if (currentBottoms.isEmpty()) {
                insertPredefinedBottoms()  // Insert predefined tops if the database is empty
            }
            bottomsDao.getAllBottoms().collect { bottoms ->
                _allBottoms.value = bottoms
            }
        }
    }

    // Method to insert predefined tops
    private suspend fun insertPredefinedBottoms() {
        bottomsList.forEach { bottom ->
            bottomsDao.insertBottom(bottom)
        }
    }

    suspend fun insertBottom(bottom: Bottoms) {
        bottomsDao.insertBottom(bottom)
    }

    suspend fun deleteAllBottoms() {
        bottomsDao.deleteAllBottoms()
    }

    suspend fun updateFavoriteStatus(bottomId: Int, isFavorite: Boolean) {
        bottomsDao.updateFavoriteStatus(bottomId, isFavorite)
    }

}