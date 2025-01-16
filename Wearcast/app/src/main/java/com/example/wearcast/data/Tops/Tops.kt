package com.example.wearcast.data.Tops

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tops_table")
data class Tops(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val color: String,
    val imageRes: String,
    val isForWomen: Boolean,
    val isFavorite: Boolean,
    val points: Int
)