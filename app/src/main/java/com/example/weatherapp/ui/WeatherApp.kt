package com.example.weatherapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.data.model.WeatherType
import com.example.weatherapp.data.remote.RetrofitClient

@Composable
fun WeatherApp() {
    var currentWeather by remember { mutableStateOf<WeatherType>(WeatherType.Sunny) }
    var city by remember { mutableStateOf("New York") }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(city) {
        isLoading = true
        error = null

        try {
            val response = RetrofitClient.weatherService.getWeather(
                city = city,
                apiKey = "901ed79b4c3850e7135853e754c81a49"  // Replace with your actual API key
            )

            val desc = response.weather.firstOrNull()?.description?.lowercase() ?: ""
            println("Description: $response")
            val sealedWeatherType = when {
                "clear" in desc -> WeatherType.Sunny
                "cloud" in desc -> WeatherType.Cloudy
                "rain"  in desc -> WeatherType.Rainy
                else            -> WeatherType.Sunny
            }.apply {
                // Update the mutable fields if you have them
                this.temperature = "${response.main.temp.toInt()} °C"
                this.description = desc
            }
            currentWeather = sealedWeatherType
        } catch (e: Exception) {
            error = e.message
        } finally {
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator()
            }
            error != null -> {
                Text("Error: $error", color = Color.Red)
            }
            else -> {
                // Show weather info
                Image(
                    painter = painterResource(id = currentWeather.conditionImage),
                    contentDescription = "Weather Condition",
                    modifier = Modifier.size(120.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = currentWeather.temperature,
                    fontSize = 48.sp
                )

                Text(
                    text = currentWeather.description,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(onClick = { city = "New York" }) { Text("New York") }
                    Button(onClick = { city = "London" }) { Text("London") }
                    Button(onClick = { city = "Tokyo" }) { Text("Tokyo") }
                }
            }
        }
    }
}
