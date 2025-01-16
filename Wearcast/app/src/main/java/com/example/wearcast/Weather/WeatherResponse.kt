package com.example.wearcast.Weather

data class WeatherResponse(
    val main: Main // Represents the "main" JSON object in the API response
)

data class Main(
    val temp: Float // Represents the "temp" field in the "main" object
)