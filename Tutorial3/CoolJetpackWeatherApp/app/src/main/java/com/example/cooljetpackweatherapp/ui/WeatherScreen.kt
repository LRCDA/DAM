package com.example.cooljetpackweatherapp.ui

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cooljetpackweatherapp.R
import com.example.cooljetpackweatherapp.data.WMOWeatherCode
import com.example.cooljetpackweatherapp.data.getWeatherCodeMap
import com.example.cooljetpackweatherapp.ui.theme.CoolJetpackWeatherAppTheme
import com.example.cooljetpackweatherapp.viewmodel.WeatherViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherUI(weatherViewModel: WeatherViewModel = viewModel()) {
    val weatherUIState by weatherViewModel.uiState.collectAsState()

    val latitude = weatherUIState.latitude
    val longitude = weatherUIState.longitude
    val temperature = weatherUIState.temperature
    val apparentTemperature = weatherUIState.apparentTemperature
    val relativeHumidity = weatherUIState.relativeHumidity
    val weatherCode = weatherUIState.weatherCode
    val precipitationProbability = weatherUIState.precipitationProbability
    val sunrise = weatherUIState.sunrise
    val sunset = weatherUIState.sunset

    val configuration = LocalConfiguration.current

    val day = true // Must change this in the future

    val mapt = getWeatherCodeMap()
    val wCode = mapt.get(weatherCode)
    val wLabel = wCode?.label ?: "Unknown"

    val wImage = when (wCode) {
        WMOWeatherCode.CLEAR_SKY,
        WMOWeatherCode.MAINLY_CLEAR,
        WMOWeatherCode.PARTLY_CLOUDY -> if (day) (wCode?.image) + "day"
            else (wCode?.image) + "night"
        else -> wCode?.image
    }

    val context = LocalContext.current
    val wIcon = context.resources.getIdentifier(
        wImage, "drawable",
        context.packageName
    )

    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    /*true*/) {
        LandscapeWeatherUI(
            wIcon,
            wLabel,
            latitude,
            longitude,
            temperature,
            apparentTemperature,
            relativeHumidity,
            weatherCode,
            precipitationProbability,
            sunrise,
            sunset,
            onLatitudeChange = { newValue-> newValue.toFloatOrNull()?.let { weatherViewModel.updateLatitude(it) }},
            onLongitudeChange = { newValue-> newValue.toFloatOrNull()?.let { weatherViewModel.updateLongitude(it) }},
            onUpdateButtonClick = {
                weatherViewModel.fetchWeather()
            }
        )
    } else {
        PortraitWeatherUI(
            wIcon,
            wLabel,
            latitude,
            longitude,
            temperature,
            apparentTemperature,
            relativeHumidity,
            weatherCode,
            precipitationProbability,
            sunrise,
            sunset,
            onLatitudeChange = { newValue-> newValue.toFloatOrNull()?.let { weatherViewModel.updateLatitude(it) }},
            onLongitudeChange = { newValue-> newValue.toFloatOrNull()?.let { weatherViewModel.updateLongitude(it) }},
            onUpdateButtonClick = {
                weatherViewModel.fetchWeather()
            }
        )
    }
}

@Composable
fun PortraitWeatherUI(
    wIcon: Int,
    wLabel: String,
    latitude: String,
    longitude: String,
    temperature: Float,
    apparentTemperature: Float,
    relativeHumidity: Int,
    weatherCode: Int,
    precipitationProbability: Int,
    sunrise: String,
    sunset: String,
    onLatitudeChange: (String) -> Unit,
    onLongitudeChange: (String) -> Unit,
    onUpdateButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(6.dp))

        //Imagem tempo
        Image(
            painter = painterResource(id = wIcon),
            contentDescription = stringResource(id = R.string.contentDescription_weatherIcon),
            modifier = Modifier.size(120.dp)
        )

        //Weather e Temperatura
        Row(
            modifier = Modifier.fillMaxWidth().padding(6.dp),
        ) {
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.lighter_blue))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = wLabel,
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))

            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.lighter_blue))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${temperature.toInt()} °C",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(8.dp))

        //Coordenadas
        Card(
            modifier = Modifier.fillMaxWidth().padding(6.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.dark_blue_transp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),

                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                WeatherCoords(stringResource(R.string.latitude_text), latitude, onLatitudeChange)
                WeatherCoords(stringResource(R.string.longitude_text), longitude, onLongitudeChange)
            }
        }

        //WeatherInfo
        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth().padding(6.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.dark_blue_transp))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                WeatherInfoRow(label = stringResource(id = R.string.sunrise_text), value = sunrise)
                WeatherInfoRow(label = stringResource(id = R.string.sunset_text), value = sunset)
                WeatherInfoRow(label = stringResource(id = R.string.humidity_text), value = "${relativeHumidity}%")
                WeatherInfoRow(label = stringResource(id = R.string.apparentTemp_text), value = "${apparentTemperature.toInt()} °C")
                WeatherInfoRow(label = stringResource(id = R.string.precipitation_text), value = "${precipitationProbability}%")
            }
        }

        //Botão
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onUpdateButtonClick,
            colors = ButtonDefaults.outlinedButtonColors(colorResource(id = R.color.orange),
                contentColor = colorResource(id = R.color.white))
        ) {
            Text(
                text = stringResource(R.string.bottom_update_text),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
        }

    }

}

@Composable
fun LandscapeWeatherUI(
    wIcon: Int,
    wLabel: String,
    latitude: String,
    longitude: String,
    temperature: Float,
    apparentTemperature: Float,
    relativeHumidity: Int,
    weatherCode: Int,
    precipitationProbability: Int,
    sunrise: String,
    sunset: String,
    onLatitudeChange: (String) -> Unit,
    onLongitudeChange: (String) -> Unit,
    onUpdateButtonClick: () -> Unit
) {
    // TODO: Implement Landscape UI
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),

        verticalAlignment = Alignment.CenterVertically,
    ){
        //Botão , imagem e temperatire +wCode
        Column(
            modifier = Modifier.weight(0.5f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            //Imagem tempo
            Image(
                painter = painterResource(id = wIcon),
                contentDescription = stringResource(id = R.string.contentDescription_weatherIcon),
                modifier = Modifier.size(120.dp)
            )

            //Weather e Temperatura
            Row(
                modifier = Modifier.fillMaxWidth().padding(6.dp),
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.lighter_blue))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = wLabel,
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))

                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.lighter_blue))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${temperature.toInt()} °C",
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }

            //Botão
            Spacer(modifier = Modifier.height(4.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onUpdateButtonClick,
                colors = ButtonDefaults.outlinedButtonColors(colorResource(id = R.color.orange),
                    contentColor = colorResource(id = R.color.white))
            ) {
                Text(
                    text = stringResource(R.string.bottom_update_text),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        //Latitude e Longitude, weatherInfo
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            //Coordenadas
            Card(
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.dark_blue_transp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),

                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    WeatherCoords(stringResource(R.string.latitude_text), latitude, onLatitudeChange)
                    WeatherCoords(stringResource(R.string.longitude_text), longitude, onLongitudeChange)
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            //Weather Info
            Card(
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.dark_blue_transp))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    WeatherInfoRow(label = stringResource(id = R.string.sunrise_text), value = sunrise)
                    WeatherInfoRow(label = stringResource(id = R.string.sunset_text), value = sunset)
                    WeatherInfoRow(label = stringResource(id = R.string.humidity_text), value = "${relativeHumidity}%")
                    WeatherInfoRow(label = stringResource(id = R.string.apparentTemp_text), value = "${apparentTemperature.toInt()} °C")
                    WeatherInfoRow(label = stringResource(id = R.string.precipitation_text), value = "${precipitationProbability}%")
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    CoolJetpackWeatherAppTheme {
        WeatherUI()
    }
}