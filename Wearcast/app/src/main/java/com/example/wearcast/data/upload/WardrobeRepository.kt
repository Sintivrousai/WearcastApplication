package com.example.wearcast.data.upload

import android.util.Log
import com.example.wearcast.data.Bottoms.BottomsDao
import com.example.wearcast.data.Jackets.JacketsDao
import com.example.wearcast.data.Tops.TopsDao
import com.example.wearcast.data.User.UserDao

class WardrobeRepository(
    private val wardrobeDao: WardrobeDao,
    private val topsDao: TopsDao,
    private val bottomsDao: BottomsDao,
    private val jacketsDao: JacketsDao,
    private val userDao: UserDao
) {
    suspend fun getAllWardrobeItems(): List<Wardrobe> {
        return wardrobeDao.getAllWardrobeItems()
    }

    suspend fun removeFromWardrobe(wardrobe: Wardrobe) {
        wardrobeDao.removeWardrobeItem(wardrobe)
    }

    suspend fun getTopImage(name: String, color: String): String {
        return topsDao.getTopPhoto(name, color)
    }

    suspend fun getBottomImage(name: String, color: String): String {
        return bottomsDao.getBottomPhoto(name, color)
    }

    suspend fun getJacketImage(name: String, color: String): String {
        return jacketsDao.getJacketPhoto(name, color)
    }

    // Add item to wardrobe by querying Tops table
    suspend fun addToWardrobeByNameAndColor(userId: Int, type: String, name: String, color: String) {
        // Fetch the user's gender (isWoman) from the database
        val isWomen = userDao.getIsWomanByUserId(userId) // Assuming you have a method to get the user by ID

        when (type.lowercase()) {
            "tops" -> {
                val item = topsDao.getTopByNameAndColor(name, color)
                item?.let {
                    val wardrobeItem = Wardrobe(
                        type = type,
                        name = it.name,
                        color = it.color,
                        imageResId = it.imageRes,
                        isForWomen = isWomen, // Use isForWomen based on user's gender
                        isFavorite = it.isFavorite,
                        points = it.points,
                        userId = userId // Add user_id here
                    )
                    wardrobeDao.addWardrobeItem(wardrobeItem)
                }
            }
            "bottoms" -> {
                val item = bottomsDao.getBottomByNameAndColor(name, color)
                item?.let {
                    val wardrobeItem = Wardrobe(
                        type = type,
                        name = it.name,
                        color = it.color,
                        imageResId = it.imageRes,
                        isForWomen =  isWomen, // Use isForWomen based on user's gender
                        isFavorite = it.isFavorite,
                        points = it.points,
                        userId = userId // Add user_id here
                    )
                    wardrobeDao.addWardrobeItem(wardrobeItem)
                }
            }
            "jackets" -> {
                val item = jacketsDao.getJacketByNameAndColor(name, color)
                item?.let {
                    val wardrobeItem = Wardrobe(
                        type = type,
                        name = it.name,
                        color = it.color,
                        imageResId = it.imageRes,
                        isForWomen =  isWomen, // Use isForWomen based on user's gender
                        isFavorite = it.isFavorite,
                        points = it.points,
                        userId = userId // Add user_id here
                    )
                    wardrobeDao.addWardrobeItem(wardrobeItem)
                    Log.d("UploadScreen", "Type: $type, Name: $name, Color: $color, User ID: $userId")
                    Log.d("UploadScreen", "Retrieved item: $item")
                }
            }
            else -> {
                println("Invalid type: $type")
            }
        }
    }

    suspend fun getUserIdByUsername(username: String): Int? {
            return userDao.getUserIdByUsername(username)
        }

    suspend fun getAllWardrobeItemsForUser(userId: Int): List<Wardrobe> {
        return wardrobeDao.getWardrobeItemsByUserId(userId)
    }

    suspend fun getLastAddedItem(): Wardrobe? {
        val items = getAllWardrobeItems()
        return items.lastOrNull()
    }

    suspend fun updateFavoriteStatus(topId: Int, isFavorite: Boolean) {
        wardrobeDao.updateFavoriteStatus(topId, isFavorite)
    }
}
