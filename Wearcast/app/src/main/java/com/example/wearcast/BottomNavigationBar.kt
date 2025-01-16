package com.example.wearcast

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    BottomNavigation(
        backgroundColor = Color(0xFF0F4673),
        contentColor = Color.White
    ) {
        BottomNavigationItem(
            selected = currentRoute == "welcomePage",
            onClick = { navController.navigate("welcomePage") },
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.home), // Replace with your custom image
                    contentDescription = "Home",
                    modifier = Modifier.size(32.dp), // Set the icon size here
                    colorFilter = ColorFilter.tint(Color.White) // Make the icon white
                )
            },
            label = {
                Text(
                    text = "Home",
                    color = Color.White // Set the text color to white
                )
            }
        )

        BottomNavigationItem(
            selected = currentRoute == "wardrobeTable",
            onClick = { navController.navigate("wardrobeTable") },
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.wardrobe), // Replace with your custom image
                    contentDescription = "Wardrobe",
                    modifier = Modifier.size(32.dp), // Set the icon size here
                    colorFilter = ColorFilter.tint(Color.White) // Make the icon white
                )
            },
            label = {
                Text(
                    text = "Wardrobe",
                    color = Color.White // Set the text color to white
                )
            }
        )

        BottomNavigationItem(
            selected = currentRoute == "profile",
            onClick = { navController.navigate("profile") },
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.user), // Replace with your custom image
                    contentDescription = "User",
                    modifier = Modifier.size(32.dp), // Set the icon size here
                    colorFilter = ColorFilter.tint(Color.White) // Make the icon white
                )
            },
            label = {
                Text(
                    text = "Profile",
                    color = Color.White // Set the text color to white
                )
            }
        )
    }
}