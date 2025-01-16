package com.example.wearcast.data.upload

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wearcast.R
import com.example.wearcast.data.wardrobe.LastAddedItemCard
import com.example.wearcast.data.wardrobe.getImageResId

@Composable
fun UploadScreen(username: String, uploadViewModel: UploadViewModel = viewModel()) {
    var type by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }

    val types = listOf("tops", "bottoms", "jackets")
    val optionsMap = mapOf(
        "tops" to listOf("Crop Top", "Tank Top", "T-shirt", "Blouse", "Shirt", "Sweater", "Hoodie"),
        "bottoms" to listOf("Shorts", "Mini Skirt", "Leggings", "Long Skirt", "Trousers", "Jeans", "Sweatpants"),
        "jackets" to listOf("Leather", "Denim", "Puffer", "Blazer", "Sportswear", "Coat")
    )
    val colors = listOf(
        "black", "white", "brown", "grey", "beige", "red", "orange", "pink",
        "purple", "blue", "light_blue", "green", "yellow", "bordeaux"
    )

    val nameOptions = optionsMap[type] ?: emptyList()

    // Collect the last added item from the ViewModel
    val lastAddedItem by uploadViewModel.lastAddedItem.collectAsState()

    // State to store the resolved user_id
    var userId by remember { mutableStateOf<Int?>(null) }

    // Resolve the user_id when the screen is loaded
    LaunchedEffect(username) {
        userId = uploadViewModel.getUserIdByUsername(username)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top // Ensure content starts from the top
    ) {
        // Title Section (Upload New Item with Icon)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically // Align vertically centered
        ) {
            Icon(
                painter = painterResource(id = R.drawable.upload_icon),
                contentDescription = "Upload Icon",
                modifier = Modifier.size(24.dp), // Adjust the size of the icon
                tint = Color(0xFF0F4673)
            )
            Spacer(modifier = Modifier.width(8.dp)) // Adds space between the icon and text

            Text(
                text = "Upload New Item",
                style = MaterialTheme.typography.h4.copy(
                    color = Color(0xFF0F4673),
                    fontSize = 36.sp
                ),
                textAlign = TextAlign.Start
            )
        }

        Spacer(modifier = Modifier.height(2.dp)) // Space between title and divider
        Divider(
            color = Color.Gray, // Set the color of the line
            thickness = 1.dp,   // Set the thickness of the line
            modifier = Modifier.fillMaxWidth() // Make the line span the entire screen width
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Dropdown for Type
        SimpleDropdownMenu(label = "Type", options = types, selectedValue = type) {
            type = it
            name = "" // Clear the name when the type changes
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Dropdown for Name
        SimpleDropdownMenu(label = "Name", options = nameOptions, selectedValue = name) {
            name = it
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Dropdown for Color
        SimpleDropdownMenu(label = "Color", options = colors, selectedValue = color) {
            color = it
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add Button
        Box(
            modifier = Modifier
                .fillMaxWidth(), // Make the Box take up the full width
            contentAlignment = Alignment.Center // Center the button inside the Box
        ) {
            Button(
                onClick = {
                    uploadViewModel.addTypedItemToWardrobe(userId ?: -1 , type, name, color)
                },
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 16.dp) // Space above the button
                    .width(80.dp) // Set the button's width
                    .height(34.dp), // Set the button's height
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0F4673)), // Use backgroundColor instead of containerColor
                shape = RoundedCornerShape(16.dp), // Set rounded corners
                contentPadding = PaddingValues(0.dp)// Removes additional padding inside the button
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(), // Ensures the Box fills the button
                    contentAlignment = Alignment.Center // Centers the text within the Box
                ) {
                    Text(text = "Add", fontSize = 18.sp, color = Color.White)
                }
            }
        }

        // Last Added Item
        lastAddedItem?.let { item ->
            Text("Successfully added:", style = MaterialTheme.typography.h6)
            val imageResId = getImageResId(item.imageResId)

            LastAddedItemCard(
                name = item.name,
                color = item.color,
                imageResId = imageResId
            )

            if (imageResId != null) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .padding(top = 8.dp)
                )
            } else {
                Text("Image not found", color = MaterialTheme.colors.error)
            }
        }
    }
}

@Composable
fun SimpleDropdownMenu(
    label: String,
    options: List<String>,
    selectedValue: String,
    onValueSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(text = label, style = MaterialTheme.typography.subtitle2)
        Box {
            TextField(
                value = selectedValue,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown"
                        )
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = MaterialTheme.colors.onSurface,
                    backgroundColor = MaterialTheme.colors.surface,
                    focusedIndicatorColor = MaterialTheme.colors.primary
                )
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                options.forEach { option ->
                    DropdownMenuItem(onClick = {
                        onValueSelected(option)
                        expanded = false
                    }) {
                        Text(option)
                    }
                }
            }
        }
    }
}