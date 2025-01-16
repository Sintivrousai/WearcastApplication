package com.example.wearcast.Weather

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class WeatherRepository {
    private val api = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(OpenWeatherApi::class.java)

    suspend fun fetchTemperature(city: String): String {
        return try {
            val response = api.getWeather(city, "4f042fbe0e7d3b7d25672cdbfb269af0", "metric")
            if (response.isSuccessful) {
                val temp = response.body()?.main?.temp
                temp?.let { "${it.toInt()}°C" } ?: "No temperature data found."
            } else {
                "City not found."
            }
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }
}