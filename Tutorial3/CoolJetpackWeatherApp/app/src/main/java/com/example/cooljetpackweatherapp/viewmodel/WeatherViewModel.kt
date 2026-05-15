package com.example.cooljetpackweatherapp.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cooljetpackweatherapp.data.WeatherAPIClient
import com.example.cooljetpackweatherapp.ui.WeatherUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class WeatherViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(WeatherUIState())
    val uiState: StateFlow<WeatherUIState> = _uiState.asStateFlow() //asStateFlow: read-only state flow

    fun updateLatitude(newValue: Float) {
       _uiState.update {it.copy(latitude = newValue.toString())}
    }

    fun updateLongitude(newValue: Float) {
        _uiState.update {it.copy(longitude = newValue.toString())}
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchWeather() {
        /*
        * - Fazer request para a API
        * - Fazer update das váriáveis do UIState
        * */

        viewModelScope.launch { //Isto equivale a Thread no trabalho anterior. Coroutine.

            val lat = _uiState.value.latitude.toFloatOrNull() ?: 38.76f
            val lon = _uiState.value.longitude.toFloatOrNull() ?: -9.12f

            val weather = WeatherAPIClient.getWeather(lat, lon)

            println("======================== RESPOSTA WEATHER: ${weather}")

            if(weather != null){
                /*Para:
                * - precipitation estar atualizado pelas horas certas
                * - sunrise e sunset estarem atualizados pelas datas certas
                * */

                //TimeZone
                val zoneId = ZoneId.of(weather.timezone)
                val nowZone = ZonedDateTime.now(zoneId)

                //Formatos
                val formaterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val formaterHour = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:00")

                //Current Indexes
                val dayIndex = weather.daily.time.indexOf(nowZone.format(formaterDate))
                val hourIndex = weather.hourly.time.indexOf(nowZone.format(formaterHour))

                //Variaveis
                val precipitation = weather.hourly.precipitation_probability.getOrNull(hourIndex) ?: 0
                val sunrise = weather.daily.sunrise.getOrNull(dayIndex)?.takeLast(5) ?: "" //Ultimos 5 caracteres = horas
                val sunset = weather.daily.sunset.getOrNull(dayIndex)?.takeLast(5) ?: "" // ""2026-05-13T05:26""


                println("======================== PERTO DO UPDATE EM WEATHERVIEWMODEL")
                _uiState.update {
                    println("======================== ENTROU NO UPDATE EM WEATHERVIEWMODEL")
                    it.copy(
                        temperature = weather.current.temperature_2m,
                        apparentTemperature = weather.current.apparent_temperature,
                        relativeHumidity = weather.current.relative_humidity_2m,
                        weatherCode = weather.current.weather_code,

                        precipitationProbability = precipitation,
                        sunrise = sunrise,
                        sunset = sunset,
                    )
                }
            }

        }
    }
}