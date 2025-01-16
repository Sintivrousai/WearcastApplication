package com.example.wearcast.data.upload

import androidx.room.*

@Dao
interface WardrobeDao {

    @Insert
    suspend fun addWardrobeItem(wardrobe: Wardrobe)

    @Query("SELECT * FROM wardrobe_table")
    suspend fun getAllWardrobeItems(): List<Wardrobe>

    @Query("SELECT * FROM wardrobe_table WHERE userId = :userId")
    suspend fun getWardrobeItemsByUserId(userId: Int): List<Wardrobe>

    @Delete
    suspend fun removeWardrobeItem(wardrobe: Wardrobe)

    @Query("UPDATE wardrobe_table SET isFavorite = :isFavorite WHERE id = :itemId")
    suspend fun updateFavoriteStatus(itemId: Int, isFavorite: Boolean)

    @Query("SELECT * FROM wardrobe_table WHERE userId = :userId")
    suspend fun getWardrobeItemsForUser(userId: String): List<Wardrobe>

}
