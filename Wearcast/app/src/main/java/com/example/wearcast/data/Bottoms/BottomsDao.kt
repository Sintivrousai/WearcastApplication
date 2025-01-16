package com.example.wearcast.data.Bottoms

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.wearcast.data.Tops.Tops
import kotlinx.coroutines.flow.Flow

@Dao
interface BottomsDao {
    @Query("SELECT * FROM bottoms_table")
    fun getAllBottoms(): Flow<List<Bottoms>>

    @Insert
    suspend fun insertBottom(bottom: Bottoms)

    @Query("DELETE FROM bottoms_table")
    suspend fun deleteAllBottoms()

    @Query("UPDATE bottoms_table SET isFavorite = :isFavorite WHERE id = :bottomId")
    suspend fun updateFavoriteStatus(bottomId: Int, isFavorite: Boolean)

    @Query("SELECT * FROM bottoms_table WHERE name = :name AND color = :color LIMIT 1")
    suspend fun getBottomByNameAndColor(name: String, color: String): Bottoms?

    @Query("SELECT imageRes FROM bottoms_table WHERE name = :name AND color = :color LIMIT 1")
    suspend fun getBottomPhoto(name: String, color: String): String

}