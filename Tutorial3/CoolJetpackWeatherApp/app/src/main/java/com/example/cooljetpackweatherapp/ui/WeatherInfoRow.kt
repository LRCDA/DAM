package com.example.cooljetpackweatherapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cooljetpackweatherapp.R
import com.example.cooljetpackweatherapp.ui.theme.CoolJetpackWeatherAppTheme

@Composable
fun WeatherInfoRow(
    label: String,
    value: String,
){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        ){
        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                //Label
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.black),
                )
            }
        }

        Spacer(Modifier.width(14.dp))

        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ){
                //Value
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.black),
                )
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun WeatherInfoRowPreview() {
    CoolJetpackWeatherAppTheme {
        WeatherInfoRow(
            label = "Humidity",
            value = "65%"
        )
    }
}