package com.example.wearcast.data.Jackets

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.wearcast.data.Tops.Tops
import kotlinx.coroutines.flow.Flow

@Dao
interface JacketsDao {
    @Query("SELECT * FROM jackets_table")
    fun getAllJackets(): Flow<List<Jackets>>

    @Insert
    suspend fun insertJacket(jacket: Jackets)

    @Query("DELETE FROM jackets_table")
    suspend fun deleteAllJackets()

    @Query("UPDATE jackets_table SET isFavorite = :isFavorite WHERE id = :jacketId")
    suspend fun updateFavoriteStatus(jacketId: Int, isFavorite: Boolean)

    @Query("SELECT * FROM jackets_table WHERE name = :name AND color = :color LIMIT 1")
    suspend fun getJacketByNameAndColor(name: String, color: String): Jackets?

    @Query("SELECT imageRes FROM jackets_table WHERE name = :name AND color = :color LIMIT 1")
    suspend fun getJacketPhoto(name: String, color: String): String

}