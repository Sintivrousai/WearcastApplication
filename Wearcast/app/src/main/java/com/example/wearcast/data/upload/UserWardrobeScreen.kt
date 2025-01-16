package com.example.wearcast.data.upload

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wearcast.R
import com.example.wearcast.data.wardrobe.WardrobeItemCard
import com.example.wearcast.data.wardrobe.getImageResId
import com.example.wearcast.data.wardrobe.FilterDropdown

@Composable
fun UserWardrobeScreen(
    username: String,
    wardrobeTableViewModel: WardrobeTableViewModel,
    onUploadClick: () -> Unit
) {
    val wardrobeItems by wardrobeTableViewModel.wardrobeItems.collectAsState(initial = emptyList())

    var userId by remember { mutableStateOf<Int?>(null) }
    val (filter, setFilter) = remember { mutableStateOf("All") }
    var debugText by remember { mutableStateOf("") }

    LaunchedEffect(username) {
        debugText = "Fetching user ID for username: $username"
        userId = wardrobeTableViewModel.getUserIdByUsername(username)
        if (userId != null) {
            debugText = "User ID resolved: $userId. Fetching wardrobe items..."
            wardrobeTableViewModel.fetchWardrobeItemsForUser(userId!!)
        } else {
            debugText = "Failed to resolve user ID for username: $username"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {


        // Header Title
        Text(
            text = "Your Wardrobe",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)
        )

        Text(
            text = "Property of $username",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Gray,
                fontSize = 16.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Blue line divider
        Divider(
            color = Color(0xFF0D47A1),
            thickness = 2.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Filter Dropdown
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Filter items:",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                ),
                color = Color.Black
            )
            FilterDropdown(filter = filter, onFilterSelected = setFilter)
        }

        // Wardrobe Items Grid
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 150.dp),
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp)
        ) {
            val filteredItems = when (filter) {
                "Women" -> wardrobeItems.filter { it.isForWomen }
                "Men" -> wardrobeItems.filter { !it.isForWomen }
                else -> wardrobeItems
            }

            items(filteredItems.size) { index ->
                val item = filteredItems[index]
                WardrobeItemCard(
                    name = item.name,
                    color = item.color,
                    imageResId = getImageResId(item.imageResId),
                    isFavorite = item.isFavorite,
                    onFavoriteClick = { isFavorite ->
                        wardrobeTableViewModel.updateFavoriteStatus(item.id, isFavorite)
                        debugText = "Updated favorite status for item ID: ${item.id} to $isFavorite"
                    },
                    onDeleteClick = {
                        wardrobeTableViewModel.removeWardrobeItem(item)
                        debugText = "Deleted item ID: ${item.id}"
                    }
                )
            }
        }

        // Display Item Count
        Text(
            text = "👕 Currently displaying ${wardrobeItems.size} items in your wardrobe! 👗",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Black,
                lineHeight = 20.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

// Upload Button
        Button(
            onClick = onUploadClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0F4673), // Matches the nav bar blue
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text(
                text = "Upload a New Item",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}
