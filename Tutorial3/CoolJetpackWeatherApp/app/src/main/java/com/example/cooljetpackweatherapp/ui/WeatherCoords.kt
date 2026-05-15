package com.example.cooljetpackweatherapp.ui

import android.widget.EditText
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cooljetpackweatherapp.R
import com.example.cooljetpackweatherapp.ui.theme.CoolJetpackWeatherAppTheme

@Composable
fun WeatherCoords (
    label: String,
    value: String,
    onValueChange: (String) -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth().padding(6.dp),

        verticalAlignment = Alignment.CenterVertically,
    ){
        //Label
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ){
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.black),
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        //Coords
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ){
            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorResource(R.color.white),
                    unfocusedContainerColor = colorResource(R.color.orange),
                    focusedIndicatorColor = colorResource(R.color.orange),
                    unfocusedIndicatorColor = colorResource(R.color.white),

                    focusedTextColor = colorResource(R.color.black),
                    unfocusedTextColor = colorResource(R.color.black),

                    ),

                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                ),
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun WeatherCoordsPreview() {
    CoolJetpackWeatherAppTheme {
        WeatherCoords(
            label = "Latitude",
            value = "3.865",
            onValueChange = {}
        )
    }
}