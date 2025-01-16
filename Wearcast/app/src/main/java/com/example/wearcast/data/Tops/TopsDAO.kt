package com.example.wearcast.data.Tops

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TopsDao {
    @Query("SELECT * FROM tops_table")
    fun getAllTops(): Flow<List<Tops>>

    @Insert
    suspend fun insertTop(top: Tops)

    @Query("DELETE FROM tops_table")
    suspend fun deleteAllTops()

    @Query("UPDATE tops_table SET isFavorite = :isFavorite WHERE id = :topId")
    suspend fun updateFavoriteStatus(topId: Int, isFavorite: Boolean)

    @Query("SELECT * FROM tops_table WHERE name = :name AND color = :color LIMIT 1")
    suspend fun getTopByNameAndColor(name: String, color: String): Tops?

    @Query("SELECT imageRes FROM tops_table WHERE name = :name AND color = :color LIMIT 1")
    suspend fun getTopPhoto(name: String, color: String): String
}