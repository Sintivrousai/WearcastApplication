package com.example.wearcast.data.Jackets

import com.example.wearcast.data.Tops.Tops
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class JacketsRepository(private val jacketDao: JacketsDao) {
    // Insert sample jackets
    val colors = listOf(
        "black", "white", "brown", "grey", "beige", "red", "orange", "pink",
        "purple", "blue", "light_lue", "green", "yellow", "bordeaux"
    )


    private val jacketsList: List<Jackets> by lazy {
        // Predefined items outside the loop
        val predefinedJackets = listOf(
            //all denim jackets
            Jackets(name = "Denim", color = "Blue", imageRes = "denim_blue", isForWomen = true, isFavorite = false, points = 5),
            Jackets(name = "Denim", color = "Black", imageRes = "denim_black", isForWomen = true, isFavorite = false, points = 5),
            Jackets(name = "Denim", color = "White", imageRes = "denim_white", isForWomen = true, isFavorite = false, points = 5),
            Jackets(name = "Denim", color = "Beige", imageRes = "denim_beige", isForWomen = true, isFavorite = false, points = 5),
            Jackets(name = "Denim", color = "Blue", imageRes = "denim_blue", isForWomen = false, isFavorite = false, points = 5),
            Jackets(name = "Denim", color = "Black", imageRes = "denim_black", isForWomen = false, isFavorite = false, points = 5),
            Jackets(name = "Denim", color = "White", imageRes = "denim_white", isForWomen = false, isFavorite = false, points = 5),
            Jackets(name = "Denim", color = "Beige", imageRes = "denim_beige", isForWomen = false, isFavorite = false, points = 5),

            //all leather jackets
            Jackets(name = "Leather", color = "Brown", imageRes = "leather_brown", isForWomen = true, isFavorite = false, points = 7),
            Jackets(name = "Leather", color = "Black", imageRes = "leather_black", isForWomen = true, isFavorite = false, points = 7),
            Jackets(name = "Leather", color = "White", imageRes = "leather_white", isForWomen = true, isFavorite = false, points = 7),
            Jackets(name = "Leather", color = "Bordeaux", imageRes = "leather_bordeaux", isForWomen = true, isFavorite = false, points = 7),
            Jackets(name = "Leather", color = "Brown", imageRes = "leather_brown", isForWomen = false, isFavorite = false, points = 7),
            Jackets(name = "Leather", color = "Black", imageRes = "leather_black", isForWomen = false, isFavorite = false, points = 7),
            Jackets(name = "Leather", color = "White", imageRes = "leather_white", isForWomen = false, isFavorite = false, points = 7),
            Jackets(name = "Leather", color = "Bordeaux", imageRes = "leather_bordeaux", isForWomen = false, isFavorite = false, points = 7)
        )

        // Generate other items dynamically based on colors
        val dynamicJackets = colors.flatMap { color ->
            listOf(
                Jackets(name = "Sportswear", color = color, imageRes = "sportswear_${color}", isForWomen = true, isFavorite = false, points = 6),
                Jackets(name = "Blazer", color = color, imageRes = "blazer_${color}", isForWomen = true, isFavorite = false, points = 7),
                Jackets(name = "Coat", color = color, imageRes = "coat_${color}", isForWomen = true, isFavorite = false, points = 8),
                Jackets(name = "Puffer", color = color, imageRes = "puffer_${color}", isForWomen = true, isFavorite = false, points = 9),

                Jackets(name = "Sportswear", color = color, imageRes = "t-shirt_${color}", isForWomen = false, isFavorite = false, points = 6),
                Jackets(name = "Blazer", color = color, imageRes = "shirt_${color}", isForWomen = false, isFavorite = false, points = 7),
                Jackets(name = "Coat", color = color, imageRes = "shirt_${color}", isForWomen = false, isFavorite = false, points = 8),
                Jackets(name = "Puffer", color = color, imageRes = "hoodies_${color}", isForWomen = false, isFavorite = false, points = 9)
            )
        }

        // Combine predefined and dynamically generated items
        predefinedJackets + dynamicJackets
    }

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val _allJackets: MutableStateFlow<List<Jackets>> = MutableStateFlow(emptyList())
    val allJackets: StateFlow<List<Jackets>> = _allJackets

    init {
        // Insert predefined tops if the database is empty
        coroutineScope.launch {
            val currentJackets = jacketDao.getAllJackets().first()  // Get the current list from the database
            if (currentJackets.isEmpty()) {
                insertPredefinedJackets()  // Insert predefined tops if the database is empty
            }
            jacketDao.getAllJackets().collect { jackets ->
                _allJackets.value = jackets
            }
        }
    }

    // Method to insert predefined tops
    private suspend fun insertPredefinedJackets() {
        jacketsList.forEach { jacket ->
            jacketDao.insertJacket(jacket)
        }
    }

    suspend fun insertJacket(jacket: Jackets) {
        jacketDao.insertJacket(jacket)
    }

    suspend fun deleteAllJackets() {
        jacketDao.deleteAllJackets()
    }

    suspend fun updateFavoriteStatus(jacketId: Int, isFavorite: Boolean) {
        jacketDao.updateFavoriteStatus(jacketId, isFavorite)
    }
}