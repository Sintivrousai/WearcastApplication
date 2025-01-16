package com.example.wearcast.OutfitCombinations

data class Outfit(
    val outfitId: Int,
    val topId: Int,
    val topColor: String,
    val bottomId: Int,
    val bottomColor: String,
    val jacketId: Int,
    val jacketColor: String,
    val topPoints: Int,
    val bottomPoints: Int,
    val jacketPoints: Int,
    val points:Int,
    val topPhoto:String,
    val bottomPhoto:String,
    val jacketPhoto:String
)
