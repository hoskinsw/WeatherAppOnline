// Weather.kt
package com.example.weatherapp.data.model

import com.example.weatherapp.R

sealed class WeatherType(
    val conditionImage: Int,
    var temperature: String = "",
    var description: String = ""
) {
    object Sunny : WeatherType(
        conditionImage = R.drawable.ic_sunny // for example
    )

    object Cloudy : WeatherType(
        conditionImage = R.drawable.ic_cloudy
    )

    object Rainy : WeatherType(
        conditionImage = R.drawable.ic_rainy
    )

    fun copy(
        temperature: String = this.temperature,
        description: String = this.description
    ): WeatherType {
        // This is just an example extension to update
        // the temperature/description on an existing WeatherType.
        return when (this) {
            Sunny -> Sunny.apply {
                this.temperature = temperature
                this.description = description
            }
            Cloudy -> Cloudy.apply {
                this.temperature = temperature
                this.description = description
            }
            Rainy -> Rainy.apply {
                this.temperature = temperature
                this.description = description
            }
        }
    }
}
