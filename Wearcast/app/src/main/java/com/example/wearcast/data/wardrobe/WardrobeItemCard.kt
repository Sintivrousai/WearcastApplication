package com.example.wearcast.data.wardrobe

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.wearcast.WearcastApplication

@Composable
fun WardrobeItemCard(
    name: String,
    color: String,
    imageResId: Int?,
    isFavorite: Boolean,
    onFavoriteClick: (Boolean) -> Unit,
    onDeleteClick: () -> Unit // Callback for delete action
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (imageResId != null) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = name, color = Color.Black, style = MaterialTheme.typography.bodyMedium)
            Text(text = color, color = Color.Gray, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Favorite Button
                IconButton(onClick = { onFavoriteClick(!isFavorite) }) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color.Red else Color.Gray
                    )
                }

                // Delete Button
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Close , // Use delete icon
                        contentDescription = "Delete",
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun LastAddedItemCard(
    name: String,
    color: String,
    imageResId: Int?
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (imageResId != null) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text(
                    text = "Image not available",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = name, color = Color.Black, style = MaterialTheme.typography.bodyMedium)
            Text(text = color, color = Color.Gray, style = MaterialTheme.typography.bodySmall)
        }
    }
}

fun getImageResId(imageResName: String): Int? {
    return try {
        // Resolve the imageResName to an actual drawable resource ID
        val context = WearcastApplication.appContext // Get the app context
        val resId = context.resources.getIdentifier(imageResName, "drawable", context.packageName)
        if (resId != 0) resId else null
    } catch (e: Exception) {
        null // Return null if the resource could not be resolved
    }
}
