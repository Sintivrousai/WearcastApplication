package com.example.wearcast

import android.health.connect.datatypes.units.Temperature
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wearcast.OutfitCombinations.OutfitViewModel
import com.example.wearcast.RecommendedOutfits.DefaultOutfits
import com.example.wearcast.RecommendedOutfits.SuitableOutfits
import com.example.wearcast.data.upload.WardrobeTableViewModel


@Composable
fun WelcomePage(username: String,
                viewModel: OutfitViewModel = viewModel(),
                temperature: String,
                cityName:String,
                viewModel1: WardrobeTableViewModel = viewModel()) {
    val currentDate = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault()).format(Date())


    LaunchedEffect(username) {
        viewModel.loadOutfitsForUser(username)
    }

    //val allOutfits = viewModel.outfits.collectAsState().value
    val allOutfits by viewModel.outfits.collectAsState(initial = emptyList())

    // Pass the outfits to AvailableOutfits
    val suitableOutfits = SuitableOutfits(temperature,allOutfits)

    // Get the outfits that are available in the wardrobe
    var allSuitableOutfits = suitableOutfits.getSuitableOutfits()

    var filteredOutfits=allSuitableOutfits



    Column(modifier = Modifier.fillMaxSize()) {
        // Upper box for temperature with rounded corners
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f) // Take up a portion of the screen height
                //.clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)) // Apply rounded corners at the bottom
                .background(Color(0xFF0F4673)), // Vivid blue background
            contentAlignment = Alignment.CenterStart // Align content to start (left)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Transparent and wider text for "18°C"
                Text(
                    text = temperature,
                    style = MaterialTheme.typography.displayLarge.copy(
                        color = Color.White.copy(alpha = 0.6f) // 60% opacity
                    )
                )
                // Vertical separator with ombre effect
                Spacer(modifier = Modifier.width(8.dp)) // Space before the line
                Box(
                    modifier = Modifier
                        .width(3.dp) // Line thickness
                        .height(60.dp) // Line height
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent, // Start (transparent)
                                    Color.White.copy(alpha = 0.6f), // Middle (visible)
                                    Color.Transparent // End (transparent)
                                )
                            )
                        )
                )
                Spacer(modifier = Modifier.width(8.dp)) // Space after the line
                // Column for date and location
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    // Date text
                    Text(
                        text = currentDate,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White,
                            fontSize = 24.sp // Larger font size for the date
                        ),
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(8.dp)) // Space between date and location
                    // Location row
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Location icon in white
                        Image(
                            painter = painterResource(id = R.drawable.location), // Replace with your resource ID
                            contentDescription = "Location Icon",
                            modifier = Modifier
                                .size(24.dp) // Same size as the date text
                                .padding(end = 8.dp), // Space between icon and text
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.White) // Make the icon white
                        )
                        // Location text
                        Text(
                            text = cityName,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = Color.White,
                                fontSize = 24.sp // Same size as the date text
                            ),
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp)) // Space between date and location
        // Title for "Recommended Outfits for today"

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2.6f) ,// Take up a portion of the screen height
            verticalArrangement = Arrangement.spacedBy(16.dp) // Adds spacing between each carousel
        ) {
            if (allSuitableOutfits.isEmpty()) {
                filteredOutfits = DefaultOutfits.makeOutfits(temperature)  //put some defaulte recommendations

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "You don't have available outfits.Please upload more items.You can take some inspiration from the outfits below." ,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.Gray,
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }
            } else {
                // Title and Divider as the first item
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 18.dp, horizontal = 16.dp)
                    ) {
                        Text(
                            text = "Recommended Outfits",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color.Black,
                                fontSize = 30.sp
                            ),
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.height(2.dp)) // Space between title and divider
                        Divider(
                            color = Color.Gray, // Set the color of the line
                            thickness = 1.dp,   // Set the thickness of the line
                            modifier = Modifier.fillMaxWidth() // Make the line span the entire screen width
                        )
                    }
                }
            }

            val outfitsToDisplay = if (filteredOutfits.size > 5) filteredOutfits.take(5) else filteredOutfits

            itemsIndexed(outfitsToDisplay) { index, allOutfit ->
                OutfitCarousel(outfit = allOutfit) // Add carousel for each outfit
                if (index < outfitsToDisplay.size - 1) { // Check if it's not the last item
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider(
                        color = Color.Gray,
                        thickness = 1.dp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

    }
}