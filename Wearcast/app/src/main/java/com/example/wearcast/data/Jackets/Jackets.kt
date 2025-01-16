package com.example.wearcast.data.Jackets

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jackets_table")
data class Jackets(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val color: String,
    val imageRes: String,
    val isForWomen: Boolean, // Column for gender
    val isFavorite: Boolean, // Column for favorite status
    val points: Int // New column for points
)