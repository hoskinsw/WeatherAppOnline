package com.example.weatherapp.data.model

data class WeatherResponse(
    val main: Main,
    val weather: List<WeatherDetails>,
    val name: String // City name
)

data class WeatherDetails(
    val description: String,
    val icon: String
)

data class Main(
    val temp: Double // Temperature in Kelvin (or metric if you specify "units=metric")
)

//data class Weather(
//    val description: String, // e.g., "clear sky"
//    val icon: String         // e.g., "01d"
//)
