package com.example.cooljetpackweatherapp.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object WeatherAPIClient {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }) //Ignores extra JSON fields
        }
    }
    suspend fun getWeather(lat: Float, lon: Float): WeatherData? {
        println("=========LAT: $lat, LON: $lon =========")
        val reqString = buildString {
            append("https://api.open-meteo.com/v1/forecast?")
            append("latitude=${lat}&longitude=${lon}&")
            //Timezone
            append("timezone=auto&") //Para obter a timezone
            //Daily
            append("daily=sunrise,sunset&")
            //Hourly
            append("hourly=precipitation_probability&")
            //Current
            append("current=apparent_temperature,relative_humidity_2m,temperature_2m,weather_code")
        }
        println("Getting URL: $reqString")

        return try {
            client.get(reqString).body() //Parses JSON into WeatherData
        } catch (e: Exception) {
            e.printStackTrace()
            println("================== AQUI ======================")
            println("================== Error: ${e.message}")
            null
        }
    }
}
