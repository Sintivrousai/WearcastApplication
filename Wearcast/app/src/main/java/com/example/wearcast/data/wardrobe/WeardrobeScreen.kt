package com.example.wearcast.data.wardrobe

import androidx.compose.foundation.layout.* // Updated imports
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
/*
@Composable
fun WardrobeScreen(wardrobeViewModel: WardrobeViewModel) {
    // Collect wardrobe items from the ViewModel
    val wardrobeItems by wardrobeViewModel.wardrobeItems.collectAsState(
        initial = Triple(emptyList(), emptyList(), emptyList())
    )

    val (tops, bottoms, jackets) = wardrobeItems
    val (filter, setFilter) = remember { mutableStateOf("All") }

    Column(modifier = Modifier.fillMaxSize()) {
        // Filter Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            FilterDropdown(filter = filter, onFilterSelected = setFilter)
        }

        // LazyVerticalGrid for displaying items
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 150.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {

            // Apply filtering logic
            val filteredTops = when (filter) {
                "Women" -> tops.filter { it.isForWomen }
                "Men" -> tops.filter { !it.isForWomen }
                else -> tops
            }

            val filteredBottoms = when (filter) {
                "Women" -> bottoms.filter { it.isForWomen }
                "Men" -> bottoms.filter { !it.isForWomen }
                else -> bottoms
            }

            val filteredJackets = when (filter) {
                "Women" -> jackets.filter { it.isForWomen }
                "Men" -> jackets.filter { !it.isForWomen }
                else -> jackets
            }

 missing delete icon because its the default items. if used create a second wardrobe card
            // Display Tops
            items(filteredTops.size) { index ->
                val top = filteredTops[index]
                WardrobeItemCard(
                    name = top.name,
                    color = top.color,
                    imageResId = getImageResId(top.imageRes),
                    isFavorite = top.isFavorite,
                    onFavoriteClick = { isFavorite ->
                        wardrobeViewModel.updateTopFavoriteStatus(top.id, isFavorite)
                    }
                )
            }

            // Display Bottoms
            items(filteredBottoms.size) { index ->
                val bottom = filteredBottoms[index]
                WardrobeItemCard(
                    name = bottom.name,
                    color = bottom.color,
                    imageResId = getImageResId(bottom.imageRes),
                    isFavorite = bottom.isFavorite,
                    onFavoriteClick = { isFavorite ->
                        wardrobeViewModel.updateBottomFavoriteStatus(bottom.id, isFavorite)
                    }
                )
            }
            // Display Jackets
            items(filteredJackets.size) { index ->
                val jacket = filteredJackets[index]
                WardrobeItemCard(
                    name = jacket.name,
                    color = jacket.color,
                    imageResId = getImageResId(jacket.imageRes),
                    isFavorite = jacket.isFavorite,
                    onFavoriteClick = { isFavorite ->
                        wardrobeViewModel.updateJacketFavoriteStatus(jacket.id, isFavorite)
                    }
                )
            }
        }
    }
}
*/

@Composable
fun FilterDropdown(filter: String, onFilterSelected: (String) -> Unit) {
    var expanded by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(false) }

    Box(
        modifier = Modifier.wrapContentSize(Alignment.TopEnd)
    ) {
        TextButton(onClick = { expanded = true }) {
            Text(text = filter, color = MaterialTheme.colorScheme.primary)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    onFilterSelected("All")
                    expanded = false
                },
                text = { Text("All") }
            )
            DropdownMenuItem(
                onClick = {
                    onFilterSelected("Women")
                    expanded = false
                },
                text = { Text("Women") }
            )
            DropdownMenuItem(
                onClick = {
                    onFilterSelected("Men")
                    expanded = false
                },
                text = { Text("Men") }
            )
        }
    }
}