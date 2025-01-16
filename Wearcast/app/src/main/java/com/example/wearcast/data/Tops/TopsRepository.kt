package com.example.wearcast.data.Tops

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TopsRepository(private val topsDao: TopsDao) {

    //colors:black, white, brown, grey, beige, red, orange, pink,
    //purple, blue, light blue, green, yellow, bordeaux
    val colors = listOf(
        "black", "white", "brown", "grey", "beige", "red", "orange", "pink",
        "purple", "blue", "light_lue", "green", "yellow", "bordeaux"
    )


    private val topsList: List<Tops> by lazy {
        colors.flatMap { color ->
            listOf(
                Tops(name = "Crop Top", color = color, imageRes = "crop_top_${color}", isForWomen = true, isFavorite = false, points = 1),
                Tops(name = "Tank Top", color = color, imageRes = "tank_top_${color}_1", isForWomen = true, isFavorite = false, points = 1),
                Tops(name = "T-shirt", color = color, imageRes = "tshirt_${color}", isForWomen = true, isFavorite = false, points = 2),
                Tops(name = "Blouse", color = color, imageRes = "blouse_${color}", isForWomen = true, isFavorite = false, points = 3),
                Tops(name = "Shirt", color = color, imageRes = "shirt_${color}", isForWomen = true, isFavorite = false, points = 3),
                Tops(name = "Sweater", color = color, imageRes = "sweater_${color}", isForWomen = true, isFavorite = false, points = 4),
                Tops(name = "Hoodie", color = color, imageRes = "hoodie_${color}", isForWomen = true, isFavorite = false, points = 4),

                Tops(name = "Tank Top", color = color, imageRes = "tank_top_${color}_0", isForWomen = false, isFavorite = false, points = 1),
                Tops(name = "T-shirt", color = color, imageRes = "tshirt_${color}", isForWomen = false, isFavorite = false, points = 2),
                Tops(name = "Blouse", color = color, imageRes = "blouse_${color}", isForWomen = false, isFavorite = false, points = 3),
                Tops(name = "Shirt", color = color, imageRes = "shirt_${color}", isForWomen = false, isFavorite = false, points = 3),
                Tops(name = "Sweater", color = color, imageRes = "sweater_${color}", isForWomen = false, isFavorite = false, points = 4),
                Tops(name = "Hoodie", color = color, imageRes = "hoodie_${color}", isForWomen = false, isFavorite = false, points = 4)
            )
        }
    }

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val _allTops: MutableStateFlow<List<Tops>> = MutableStateFlow(emptyList())
    val allTops: StateFlow<List<Tops>> = _allTops

    init {
        // Insert predefined tops if the database is empty
        coroutineScope.launch {
            val currentTops = topsDao.getAllTops().first()  // Get the current list from the database
            if (currentTops.isEmpty()) {
                insertPredefinedTops()  // Insert predefined tops if the database is empty
            }
            topsDao.getAllTops().collect { tops ->
                _allTops.value = tops
            }
        }
    }

    // Method to insert predefined tops
    private suspend fun insertPredefinedTops() {
        topsList.forEach { top ->
            topsDao.insertTop(top)
        }
    }

    suspend fun insertTop(top: Tops) {
        topsDao.insertTop(top)
    }

    suspend fun deleteAllTops() {
        topsDao.deleteAllTops()
    }

    suspend fun updateFavoriteStatus(topId: Int, isFavorite: Boolean) {
        topsDao.updateFavoriteStatus(topId, isFavorite)
    }

}