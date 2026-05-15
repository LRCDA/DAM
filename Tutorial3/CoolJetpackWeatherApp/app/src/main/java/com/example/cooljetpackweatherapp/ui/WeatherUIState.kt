package com.example.cooljetpackweatherapp.ui

data class WeatherUIState (
    val latitude: String = "38.76",//Lisboa
    val longitude: String = "-9.12",
    // Current
    val temperature: Float = 0f,
    val apparentTemperature: Float = 0f,
    val relativeHumidity: Int = 0,
    val weatherCode: Int = 0,
    //Hourly
    val precipitationProbability: Int = 0,
    //Daily
    val sunrise: String = "12:00 AM",
    val sunset: String = "12:00 AM"
)