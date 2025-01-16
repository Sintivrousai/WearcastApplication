package com.example.wearcast.Weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val repository = WeatherRepository()

    private val _temperature = MutableLiveData<String>()
    val temperature: LiveData<String> get() = _temperature

    fun getTemperature(city: String) {
        viewModelScope.launch {
            _temperature.value = repository.fetchTemperature(city)
        }
    }
}