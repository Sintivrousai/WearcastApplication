package com.example.wearcast

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.wearcast.data.User.Login
import com.example.wearcast.data.User.SignUp
import com.example.wearcast.data.User.UserViewModel
import com.example.wearcast.ui.theme.WearcastTheme
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.wearcast.OutfitCombinations.OutfitViewModel
import com.example.wearcast.OutfitCombinations.OutfitViewModelFactory
import com.example.wearcast.Weather.WeatherRepository
import com.example.wearcast.data.wardrobe.WardrobeViewModel
import com.example.wearcast.data.wardrobe.WardrobeViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import android.Manifest
import android.location.Address
import android.location.Geocoder
import com.example.wearcast.data.FavoritesScreen
import com.example.wearcast.data.upload.UploadScreen
import com.example.wearcast.data.upload.UploadViewModel
import com.example.wearcast.data.upload.UploadViewModelFactory
import com.example.wearcast.data.upload.UserWardrobeScreen
import com.example.wearcast.data.upload.WardrobeTableViewModel
import com.example.wearcast.data.upload.WardrobeTableViewModelFactory
import java.util.Locale

class MainActivity : ComponentActivity() {
    private val userViewModel: UserViewModel by viewModels()
    private val weatherRepository = WeatherRepository()
    private val _temperature = mutableStateOf("Loading...")

    private lateinit var outfitViewModel: OutfitViewModel
    private lateinit var wardrobeViewModel: WardrobeViewModel
    private lateinit var uploadViewModel: UploadViewModel
    private lateinit var wardrobeTableViewModel: WardrobeTableViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val _userLocation = mutableStateOf<Location?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the view models necessary
        wardrobeViewModel = ViewModelProvider(
            this,
            WardrobeViewModelFactory(
                (application as WearcastApplication).topsRepository,
                (application as WearcastApplication).bottomsRepository,
                (application as WearcastApplication).jacketsRepository
            )
        )[WardrobeViewModel::class.java]

        uploadViewModel = ViewModelProvider(
            this,
            UploadViewModelFactory(
                (application as WearcastApplication).wardrobeRepository
            )
        )[UploadViewModel::class.java]

        wardrobeTableViewModel = ViewModelProvider(
            this,
            WardrobeTableViewModelFactory(
                (application as WearcastApplication).wardrobeRepository
            )
        )[WardrobeTableViewModel::class.java] // Corrected this line to use WardrobeTableViewModel

        // Initialize WardrobeViewModel
        outfitViewModel = ViewModelProvider(
            this,
            OutfitViewModelFactory(
                (application as WearcastApplication).outfitRepository
            )
        )[OutfitViewModel::class.java]


        setContent {
            WearcastTheme {
                val navController = rememberNavController()
                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStackEntry?.destination?.route

                val loggedInUsername by userViewModel.loggedInUsername.observeAsState("")

                // Initialize the FusedLocationProviderClient
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

                // Get the user's location
                getUserLocation()

                // Example usage: Fetch temperature for a specific city
                val cityName = _userLocation.value?.let { location ->
                    getCityFromLocation(location)
                } ?: "Paris"  // Default city if location is not available
                fetchTemperature(cityName)
                Scaffold(
                    bottomBar = {
                        if (currentRoute != "login" && currentRoute != "signup") {
                            BottomNavigationBar(navController = navController)
                        }
                    },
                    content = { paddingValues ->
                        NavHost(
                            navController = navController,
                            startDestination = "login",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        ) {
                            composable("signup") {
                                SignUp(
                                    viewModel = userViewModel,
                                    onSignUpSuccess = { navController.navigate("wardrobeTable") },
                                    onLoginClick = { navController.navigate("login") } // Add navigation to signup

                                )
                            }
                            composable("login") {
                                Login(
                                    viewModel = userViewModel,
                                    onLoginSuccess = { navController.navigate("welcomePage") },
                                    onSignUpClick = { navController.navigate("signup") } // Add navigation to signup
                                )
                            }
                            composable("welcomePage") {
                                WelcomePage(username = loggedInUsername, temperature = _temperature.value, viewModel = outfitViewModel, cityName=cityName, viewModel1= wardrobeTableViewModel)
                            }
                            composable("wardrobeTable") {
                                UserWardrobeScreen(
                                    username = loggedInUsername,
                                    wardrobeTableViewModel = wardrobeTableViewModel,
                                    onUploadClick = { navController.navigate("upload") } // This triggers the navigation to the upload page
                                )
                            }
                            composable("upload") {
                                UploadScreen(username = loggedInUsername, uploadViewModel = uploadViewModel) // Pass the viewModel to UploadScreen
                            }

                            composable("profile") {
                                ProfilePage(
                                    username = loggedInUsername,
                                    viewModel=userViewModel,
                                    onEditDetailsClick = { /* Navigate to Edit Details screen */ },
                                    onMyFavoritesClick = { navController.navigate("favorite/$loggedInUsername") }                                )
                            }
                            composable("favorite/{username}") { backStackEntry ->
                                val username = backStackEntry.arguments?.getString("username") ?: ""
                                FavoritesScreen(username = username, wardrobeTableViewModel = wardrobeTableViewModel)
                            }
                        }
                    }
                )
            }
        }
    }

    private fun fetchTemperature(city: String) {
        lifecycleScope.launch {
            val result = weatherRepository.fetchTemperature(city)
            _temperature.value = result // Update the MutableState
        }
    }

    // Function to get user's location and update the mutable state
    private fun getUserLocation() {
        // Check if location permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {

            // Get the last known location
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        // Update the mutable state with the user's location
                        _userLocation.value = location
                        println("Latitude: ${location.latitude}, Longitude: ${location.longitude}")
                    } else {
                        println("Location not available")
                    }
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                }
        } else {
            // Request permission if not granted
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1)
        }
    }
    private fun getCityFromLocation(location: Location): String {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addressList: MutableList<Address>? = geocoder.getFromLocation(location.latitude, location.longitude, 1)

        return if (addressList != null && addressList.isNotEmpty()) {
            val address = addressList[0]
            // Return the city from the address, or "Unknown City" if locality is null
            address.locality ?: "Unknown City"
        } else {
            // Return "Unknown City" if no address is found or addressList is null
            "Unknown City"
        }
    }
}