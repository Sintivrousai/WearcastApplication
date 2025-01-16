package com.example.wearcast.data.Bottoms

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bottoms_table")
data class Bottoms(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val color: String,
    val imageRes: String,
    val isForWomen: Boolean,
    val isFavorite: Boolean,
    val points: Int
)