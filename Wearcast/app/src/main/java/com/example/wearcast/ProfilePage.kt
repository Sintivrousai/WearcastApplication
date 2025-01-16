package com.example.wearcast

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.border
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material3.Icon // For the Icon composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wearcast.data.User.User
import com.example.wearcast.data.User.UserViewModel

@Composable
fun ProfilePage(
    username: String, // Pass the username here
    viewModel: UserViewModel = viewModel(),
    onEditDetailsClick: () -> Unit, // Callback when saving
    onMyFavoritesClick: () -> Unit // Pass the username to the favorites screen
) {
    var isEditing by remember { mutableStateOf(false) } // Track if editing is active
    var userId by remember { mutableStateOf<Int?>(null) } // Track the userId to update
    var editedPassword by remember { mutableStateOf("") } // Track the edited password
    var editedEmail by remember { mutableStateOf("") } // Track the edited email
    var editedIsWoman by remember { mutableStateOf(false) } // Track the edited gender (isWoman)

    // States to store initial data from the database
    var initialPassword by remember { mutableStateOf("") }
    var initialEmail by remember { mutableStateOf("") }
    var initialIsWoman by remember { mutableStateOf(false) }

    // A state to trigger a manual refresh after save
    var isDataRefreshed by remember { mutableStateOf(false) }

    // Fetch user data when username changes or when forced to refresh
    LaunchedEffect(username, isDataRefreshed) {
        val user = viewModel.getUserByUsername(username)
        userId = viewModel.getUserIdByUsername(username)

        user?.let {
            //initialPassword = it.password
            initialEmail = it.email
            initialIsWoman = it.isWoman

            // Reset editable fields with the latest data
            editedPassword = initialPassword
            editedEmail = initialEmail
            editedIsWoman = initialIsWoman
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Display Profile Picture based on gender
            ProfilePicture(isWoman = initialIsWoman) // Pass the gender value here

            Spacer(modifier = Modifier.height(16.dp))

            // Display Username
            Text(
                text = "$username",
                style = MaterialTheme.typography.titleLarge
            )

            // Show the initial data before editing
            if (!isEditing) {
                /*Text(
                    text = "Password: $initialPassword",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp)) */

                Text(
                    text = "Email: $initialEmail",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Gender: ${if (initialIsWoman) "Female" else "Male"}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            // If in editing mode, show the editable fields for password, email, and gender
            if (isEditing) {
                Spacer(modifier = Modifier.height(16.dp))

                // Password editing
                TextField(
                    value = editedPassword,
                    onValueChange = { editedPassword = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Email editing
                TextField(
                    value = editedEmail,
                    onValueChange = { editedEmail = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Gender (isWoman) editing
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Gender: ")

                    Spacer(modifier = Modifier.width(16.dp))

                    // Radio Buttons for gender selection
                    RadioButton(
                        selected = editedIsWoman,
                        onClick = { editedIsWoman = true }
                    )
                    Text("Female")

                    Spacer(modifier = Modifier.width(8.dp))

                    RadioButton(
                        selected = !editedIsWoman,
                        onClick = { editedIsWoman = false }
                    )
                    Text("Male")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Edit/Save Button
            if (isEditing) {
                // Cancel button
                Button(onClick = {
                    isEditing = false // Exit edit mode
                    editedPassword = initialPassword // Reset to initial password
                    editedEmail = initialEmail // Reset to initial email
                    editedIsWoman = initialIsWoman // Reset to initial gender
                }) {
                    Text("Cancel")
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Save button
                Button(
                    onClick = {
                        if (userId != null) {
                            // Save updated details using ViewModel only if changes are made
                            if (editedPassword != initialPassword) {
                                viewModel.updatePassword(userId!!, editedPassword)
                            }
                            if (editedEmail != initialEmail) {
                                viewModel.updateEmail(userId!!, editedEmail)
                            }
                            if (editedIsWoman != initialIsWoman) {
                                viewModel.updateGender(userId!!, editedIsWoman)
                            }
                        }
                        isEditing = false // Exit edit mode
                        onEditDetailsClick() // Optionally trigger the callback

                        // Trigger the data refresh after saving
                        isDataRefreshed = !isDataRefreshed
                    }
                ) {
                    Text("Save")
                }
            } else {
                // Edit button (not in editing mode)
                ProfileOptionItem(
                    title = "Edit Details",
                    onClick = { isEditing = true }, // Enable edit mode
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.edit), // Replace with your edit icon
                            contentDescription = "Edit Details Icon",
                            tint = Color(0xFF0F4673)
                        )
                    }
                )
            }

            // Favorites Option
            ProfileOptionItem(
                title = "My Favorites",
                onClick = { onMyFavoritesClick() },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.favorites), // Replace with your favorite icon
                        contentDescription = "Favorites Icon",
                        tint = Color(0xFF0F4673)
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))


        }
    }
}



@Composable
fun ProfileOptionItem(title: String, onClick: () -> Unit, icon: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Display the icon with a larger size
            Box(
                modifier = Modifier.size(32.dp) // Increased icon size
            ) {
                icon()
            }

            Spacer(modifier = Modifier.width(16.dp)) // Space between icon and text

            // Display the title with larger font size
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp), // Adjust font size
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ProfilePicture() {
    // Display the profile picture
    Image(
        painter = painterResource(id = R.drawable.photoprofile), // Use your image's resource ID
        contentDescription = "Profile Picture",
        modifier = Modifier
            .size(120.dp) // Set the size of the profile picture
            .clip(CircleShape) // Make it circular
            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape), // Add a border
        contentScale = ContentScale.Crop // Crop the image to fill the circle
    )
}

@Composable
fun ProfilePicture(isWoman: Boolean) {
    // Choose the image based on gender
    val imageResource = if (isWoman) {
        R.drawable.woman // Replace with your woman profile image resource ID
    } else {
        R.drawable.man // Replace with your man profile image resource ID
    }

    // Display the profile picture
    Image(
        painter = painterResource(id = imageResource), // Use dynamic image resource based on gender
        contentDescription = "Profile Picture",
        modifier = Modifier
            .size(120.dp) // Set the size of the profile picture
            .clip(CircleShape) // Make it circular
            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape), // Add a border
        contentScale = ContentScale.Crop // Crop the image to fill the circle
    )
}