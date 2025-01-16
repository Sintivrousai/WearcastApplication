package com.example.wearcast.OutfitCombinations

class ColorMatcher {
    val colors = listOf(
        "black", "white", "brown", "grey", "beige", "red", "orange", "pink",
        "purple", "blue", "light blue", "green", "yellow", "bordeaux"
    )

    fun areColorsCompatible(color1: String, color2: String): Boolean {

        val colorPairs = mapOf(
            "black" to listOf("black","white", "grey", "beige", "red", "blue", "light blue", "green", "yellow", "bordeaux", "pink"),
            "white" to listOf("black", "white", "beige", "grey", "blue", "light blue", "green", "pink", "purple", "yellow", "red"),
            "brown" to listOf("black", "white", "beige", "green", "yellow", "orange", "red", "blue"),
            "grey" to listOf("black", "white", "pink", "blue", "green", "yellow", "bordeaux"),
            "beige" to listOf("black", "white", "brown", "green", "blue", "yellow", "pink"),
            "red" to listOf("black", "white", "blue", "grey", "yellow", "beige", "pink"),
            "orange" to listOf("black", "white", "brown", "yellow", "beige", "green"),
            "pink" to listOf("black", "white", "grey", "beige", "purple", "blue", "yellow"),
            "purple" to listOf("black", "white", "grey", "pink", "blue", "bordeaux"),
            "blue" to listOf("black", "white", "grey", "beige", "red", "yellow", "pink", "light blue"),
            "light blue" to listOf("black", "white", "grey", "beige", "yellow", "green"),
            "green" to listOf("black", "white", "brown", "beige", "yellow", "blue", "light blue"),
            "yellow" to listOf("black", "white", "brown", "green", "beige", "blue", "grey", "orange"),
            "bordeaux" to listOf("black", "white", "grey", "pink", "blue", "beige")

        )
        return colorPairs[color1]?.contains(color2) ?: false
    }
}