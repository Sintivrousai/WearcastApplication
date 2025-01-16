package com.example.wearcast.Weather

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): Response<WeatherResponse>
}