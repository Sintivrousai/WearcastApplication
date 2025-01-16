package com.example.wearcast.RecommendedOutfits

import com.example.wearcast.OutfitCombinations.Outfit

class SuitableOutfits(private val temperature1:String, private val outfits: List<Outfit>) {

    // Method to filter available outfits
    fun getSuitableOutfits(): List<Outfit> {


        val temperature: Int = temperature1.replace("[^\\d]".toRegex(), "").toIntOrNull() ?: 0 // Default to 0 if not a valid number2q

        return outfits.filter { outfit ->
            outfit.isSuitableInWardrobe(temperature)
        }
    }

    // Extension function to check if an outfit is available in the wardrobe
    private fun Outfit.isSuitableInWardrobe(temperature:Int): Boolean {

        return when {
            temperature >= 30 -> {
                // Hot weather: Tops with points 1 or 2, Bottoms with points 1 or 2, No jacket
                topPoints >= 1 && topPoints <= 2 && bottomPoints >= 1 && bottomPoints <= 2 && jacketId == -1
            }

            temperature in 24..29 -> {
                // Warm weather: Similar conditions but allow more flexibility with higher points
                topPoints >= 1 && topPoints <= 2 && bottomPoints >= 1 && bottomPoints <= 3 && jacketId == -1
            }

            temperature >= 18 -> {
                // Cool weather: Allow jackets and tops/bottoms with higher points
                topPoints >= 2 && topPoints <= 3 && bottomPoints >= 2 && bottomPoints <= 5 && jacketPoints >= 5 && jacketPoints<=7
            }

            temperature >= 8 -> {
                // Cool weather: Allow jackets and tops/bottoms with higher points
                topPoints >= 3  && bottomPoints >= 4  && jacketPoints >= 7
            }

            else -> {
                // For very cold temperatures (optional, depending on your logic)

                topPoints >=4 && bottomPoints >=4  && jacketPoints >=8
            }


        }
    }

}