package com.example.wearcast.RecommendedOutfits

import com.example.wearcast.OutfitCombinations.Outfit

class DefaultOutfits {

    companion object {
    fun makeOutfits(temperature1:String): List<Outfit> {
        val temperature: Int = temperature1.replace("[^\\d]".toRegex(), "").toIntOrNull() ?: 0
        var defaultOutfits: List<Outfit> = emptyList()
        if (temperature >= 30) {
            defaultOutfits = listOf(
                Outfit(
                    1, 1, "black", 1, "black", -1,
                    " ", 1, 1, -1,
                    2, "crop_top_black", "shorts_black_1", " "
                ),

                Outfit(
                    2, 2, "white", 2, "beige", -1,
                    " ", 1, 1, -1,
                    2, "crop_top_white", "shorts_beige_1", " "
                ),
                Outfit(
                    3, 3, "black", 3, "red", -1,
                    " ", 1, 1, -1,
                    2, "tshirt_black", "mini_skirt_red", ""
                )
            )
        } else if (temperature>=24) {
            defaultOutfits = listOf(
                Outfit(
                    1, 1, "black", 1, "black", -1,
                    " ", 1, 1, -1,
                    2, "tshirt_black", "mini_skirt_black", " "
                ),

                Outfit(
                    2, 2, "white", 2, "beige", -1,
                    " ", 1, 1, -1,
                    2, "tshirt_white", "long_skirt_beige_1", " "
                ),
                Outfit(
                    3, 3, "black", 3, "red", -1,
                    " ", 1, 1, -1,
                    2, "tshirt_bordeux", "trousers_black_1", ""
                )
            )

        } else if (temperature>=18) {
            defaultOutfits = listOf(
                Outfit(
                    1, 1, "black", 1, "black", 1,
                    " black", 3, 3, 5,
                    11, "blouse_black", "jeans_black_1", "denim_black "
                ),

                Outfit(
                    2, 2, "white", 2, "blue", 2,
                    "black ", 2, 4, 6,
                    12, "tshirt_white", "jeans_blue_1", "leather_black "
                ),
                Outfit(
                    3, 3, "red", 3, "black", 3,
                    "black", 2, 4, 7,
                    2, "blouse_red", "trousers_black_1", "leather_black"
                )
            )

        } else if (temperature>=8) {
            defaultOutfits = listOf(
                Outfit(
                    1, 1, "black", 1, "black", 1,
                    " blue", 3, 3, 5,
                    11, "sweater_beige", "jeans_black_1", "coat_black"
                ),

                Outfit(
                    2, 2, "white", 2, "black", 2,
                    "black ", 2, 4, 6,
                    12, "hoodie_white", "sweatpants_black", "puffer_black"
                ),
                Outfit(
                    3, 3, "red", 3, "black", 3,
                    "black", 2, 4, 7,
                    2, "shirt_red", "trousers_black_1", "blazer_black"
                )
            )

        } else {
            defaultOutfits = listOf(
                Outfit(
                    1, 1, "black", 1, "black", 1,
                    " black", 3, 3, 5,
                    11, "hoodie_black", "sweatpants_brown", "puffer_black "
                ),

                Outfit(
                    2, 2, "white", 2, "blue", 2,
                    "black ", 2, 4, 6,
                    12, "sweater_grey", "jeans_black_1", "coat_black "
                ),
                Outfit(
                    3, 3, "red", 3, "black", 3,
                    "black", 2, 4, 7,
                    2, "sweater_black", "jeans_black_1", "coat_beige"
                )
            )

        }
        return defaultOutfits
    }
    }

    /*val defaultOutfits = listOf(
        Outfit(1, 1, "black", 1, "black", 1,
            "black", 1, 1, 5,
    7, "crop_top_black", "shorts_black_1", "denim_black")
    )
    var temperature=7

    if (temperature >= 30) {
        // Hot weather: Tops with points 1 or 2, Bottoms with points 1 or 2, No jacket
        //topPoints >= 1 && topPoints <= 2 && bottomPoints >= 1 && bottomPoints <= 2 && jacketId == -1
    } else if (temperature in 24..29) {
        // Warm weather: Similar conditions but allow more flexibility with higher points
        //topPoints >= 1 && topPoints <= 2 && bottomPoints >= 1 && bottomPoints <= 3 && jacketId == -1
    } else if (temperature >= 18) {
        // Cool weather: Allow jackets and tops/bottoms with higher points
        //topPoints >= 2 && topPoints <= 3 && bottomPoints >= 2 && bottomPoints <= 5 && jacketPoints >= 5 && jacketPoints<=7
    } else if (temperature >= 8) {
        // Cool weather: Allow jackets and tops/bottoms with higher points
        //topPoints >= 3  && bottomPoints >= 4  && jacketPoints >= 7
    } else {
        // For very cold temperatures (optional, depending on your logic)

        //topPoints >=4 && bottomPoints >=4  && jacketPoints >=8
    }*/


}