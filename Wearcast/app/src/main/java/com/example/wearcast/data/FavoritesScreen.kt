package com.example.wearcast.data

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wearcast.data.upload.WardrobeTableViewModel
import com.example.wearcast.data.wardrobe.WardrobeItemCard
import com.example.wearcast.data.wardrobe.getImageResId

@Composable
fun FavoritesScreen(
    username: String, // Pass the username directly
    wardrobeTableViewModel: WardrobeTableViewModel
) {
    // Collect wardrobe items
    val wardrobeItems by wardrobeTableViewModel.wardrobeItems.collectAsState(initial = emptyList())

    // Track user ID
    var userId by remember { mutableStateOf<Int?>(null) }

    // Filter for favorites
    val favoriteItems = wardrobeItems.filter { it.isFavorite && it.userId == userId }

    // Fetch user ID on first load
    LaunchedEffect(username) {
        userId = wardrobeTableViewModel.getUserIdByUsername(username)
        if (userId != null) {
            wardrobeTableViewModel.fetchWardrobeItemsForUser(userId!!)
        }
    }

    // UI
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Favorite Items",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            style = MaterialTheme.typography.titleLarge
        )

        if (favoriteItems.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 150.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(favoriteItems.size) { index ->
                    val item = favoriteItems[index]
                    WardrobeItemCard(
                        name = item.name,
                        color = item.color,
                        imageResId = getImageResId(item.imageResId),
                        isFavorite = item.isFavorite,
                        onFavoriteClick = { isFavorite ->
                            wardrobeTableViewModel.updateFavoriteStatus(item.id, isFavorite)
                        },
                        onDeleteClick = {
                            wardrobeTableViewModel.removeWardrobeItem(item)
                        }
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No favorite items found.")
            }
        }
    }
}
