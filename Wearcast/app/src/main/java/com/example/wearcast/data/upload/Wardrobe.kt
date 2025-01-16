package com.example.wearcast.data.upload

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wardrobe_table")
data class Wardrobe(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Define a primary key with auto-generation
    val type: String,
    val name: String,
    val color: String,
    val imageResId: String,
    val isForWomen: Boolean, // Column for gender
    val isFavorite: Boolean, // Column for favorite status
    val points: Int, // New column for points
    val userId: Int? = null // Nullable initially for migration purposes
)
