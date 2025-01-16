package com.example.wearcast.OutfitCombinations

import com.example.wearcast.data.Bottoms.*
import com.example.wearcast.data.Jackets.*
import com.example.wearcast.data.upload.WardrobeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class OutfitRepository(
    private val wardrobeRepository: WardrobeRepository,

    ) {
    private val _outfits = MutableStateFlow<List<Outfit>>(emptyList())
    val outfits: StateFlow<List<Outfit>> get() = _outfits
    private val colorMatcher = ColorMatcher()

    suspend fun generateOutfits(username: String) {

        val userId = wardrobeRepository.getUserIdByUsername(username)

        if (userId == null) {
            _outfits.value = emptyList()  // If no user is found, return empty list
            return
        }

        // Fetch wardrobe items for the specific user
        val wardrobeItems = wardrobeRepository.getAllWardrobeItemsForUser(userId)

        val topsList = wardrobeItems.filter { it.type == "tops" }
        val bottomsList = wardrobeItems.filter { it.type == "bottoms" }
        val jacketsList = wardrobeItems.filter { it.type == "jackets" }

        if (topsList.isEmpty() || bottomsList.isEmpty()) {
            _outfits.value = emptyList()
            return
        }

        val generatedOutfits = mutableListOf<Outfit>()
        var outfitId = 1

        // Create outfits without jackets
        for (top in topsList) {
            for (bottom in bottomsList) {
                if (top.isForWomen==bottom.isForWomen) {
                    if (colorMatcher.areColorsCompatible(top.color, bottom.color)) {
                        val outfit = Outfit(
                            outfitId = outfitId++,
                            topId = top.id,
                            topColor = top.color,
                            bottomId = bottom.id,
                            bottomColor = bottom.color,
                            jacketId = -1,
                            jacketColor = "none",
                            topPoints = top.points,
                            bottomPoints = bottom.points,
                            jacketPoints = -1,
                            points = top.points + bottom.points,
                            topPhoto = getTopImage(top.name,top.color),
                            bottomPhoto = getBottomImage(bottom.name,bottom.color),
                            jacketPhoto = ""
                        )

                        generatedOutfits.add(outfit)
                    }
                }
            }
        }

        // Create outfits with jackets
        for (top in topsList) {
            for (bottom in bottomsList) {
                if (top.isForWomen==bottom.isForWomen) {
                    if (colorMatcher.areColorsCompatible(top.color, bottom.color)) {
                        for (jacket in jacketsList) {
                            if (top.isForWomen==jacket.isForWomen) {
                                if (colorMatcher.areColorsCompatible(
                                        top.color,
                                        jacket.color
                                    ) && colorMatcher.areColorsCompatible(
                                        bottom.color,
                                        jacket.color
                                    )
                                ) {
                                    val outfit = Outfit(
                                        outfitId = outfitId++,
                                        topId = top.id,
                                        topColor = top.color,
                                        bottomId = bottom.id,
                                        bottomColor = bottom.color,
                                        jacketId = jacket.id,
                                        jacketColor = jacket.color,
                                        topPoints = top.points,
                                        bottomPoints = bottom.points,
                                        jacketPoints = jacket.points,
                                        points = top.points + bottom.points + jacket.points,
                                        topPhoto = getTopImage(top.name,top.color),
                                        bottomPhoto = getBottomImage(bottom.name,bottom.color),
                                        jacketPhoto = getJacketImage(jacket.name,jacket.color)
                                    )
                                    generatedOutfits.add(outfit)
                                }
                            }
                        }
                    }
                }
            }
        }

        _outfits.value = generatedOutfits
    }

    suspend fun getTopImage(name: String, color: String): String {
        return wardrobeRepository.getTopImage(name, color)
    }
    suspend fun getBottomImage(name: String, color: String): String {
        return wardrobeRepository.getBottomImage(name, color)
    }
    suspend fun getJacketImage(name: String, color: String): String {
        return wardrobeRepository.getJacketImage(name, color)
    }
}