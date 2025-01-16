package com.example.wearcast

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.wearcast.OutfitCombinations.Outfit
import com.example.wearcast.data.wardrobe.getImageResId
//import com.example.wearcast.OutfitCombinations.Outfit
import com.google.accompanist.pager.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OutfitCarousel(outfit: Outfit) {

    val context = LocalContext.current

    val top = outfit.topPhoto?.let { photo ->
        context.resources.getIdentifier(photo, "drawable", context.packageName)
    }.takeIf { it != null && it != 0 }
        ?: R.drawable.default_top // Replace 'default_image' with your actual default drawable

    val bottom = outfit.bottomPhoto?.let { photo ->
        context.resources.getIdentifier(photo, "drawable", context.packageName)
    }.takeIf { it != null && it != 0 } ?: R.drawable.default_bottom

    val jacket = outfit.jacketPhoto?.let { photo ->
        context.resources.getIdentifier(photo, "drawable", context.packageName)
    }.takeIf { it != null && it != 0 } ?: R.drawable.default_jacket

// Use the resolved IDs to populate the images list
    val images = listOf(top, bottom, jacket)

// Define the count of pages (two pages in this case)
    var pagerCount = 1
    if (outfit.jacketId != -1) {
        pagerCount = 2
    }

    HorizontalPager(
        count = pagerCount, // We have 2 pages if jacket exists, else 1 page
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp) // Adjust the height for the carousel
    ) { page ->
        when (page) {
            0 -> {
                // First slide: Top and Bottom side by side
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),  // Space between images
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Space between images
                ) {
                    // Top Image
                    Image(
                        painter = painterResource(id = images[0]),  // Top image
                        contentDescription = "Top Outfit",
                        modifier = Modifier
                            .height(250.dp)
                            .weight(1f) // Equal space for both images
                            .clip(androidx.compose.foundation.shape.RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )

                    // Bottom Image
                    Image(
                        painter = painterResource(id = images[1]),  // Bottom image
                        contentDescription = "Bottom Outfit",
                        modifier = Modifier
                            .height(250.dp)
                            .weight(1f) // Equal space for both images
                            .clip(androidx.compose.foundation.shape.RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            1 -> {
                // Second slide: Display only jacket on the left half of the screen
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),  // Space between images
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Space between images
                ) {
                    // Jacket Image (only on the left side if it exists)
                    if (outfit.jacketId != -1) {
                        Image(
                            painter = painterResource(id = images[2]),  // Jacket image
                            contentDescription = "Jacket Outfit",
                            modifier = Modifier
                                .height(250.dp)
                                .weight(1f) // Takes the left half of the space
                                .clip(androidx.compose.foundation.shape.RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        // Empty space on the left if no jacket
                        Spacer(modifier = Modifier.weight(1f)) // Adds empty space in place of missing image
                    }

                    // Empty space on the right
                    Spacer(modifier = Modifier.weight(1f)) // Right half will be empty
                }
            }
        }
    }
}