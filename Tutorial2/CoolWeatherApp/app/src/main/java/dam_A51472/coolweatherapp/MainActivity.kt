package dam_A51472.coolweatherapp

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.URL
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        var lat = getCoords().first
        var long = getCoords().second
        fetchWeatherData(lat,long).start()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Botão
        val updatebutton: Button = findViewById(R.id.botaoUpdate)

        updatebutton.setOnClickListener{
            //lat = 37.56f
            //long = 126.97f
            lat = getCoords().first
            long = getCoords().second
            fetchWeatherData(lat,long).start()
        }

        // Declarar a variável day
        val day = true //mudar para false para testar tema noite

        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT-> {
                if (day) {
                    setTheme(R.style.Theme_Day)
                } else {
                    setTheme(R.style.Theme_Night)
                }
            }
            Configuration.ORIENTATION_LANDSCAPE-> {
                if (day) {
                    setTheme(R.style.Theme_Day_Land)
                } else {
                    setTheme(R.style.Theme_Night_Land)
                }
            }
        }

    }

    private fun WeatherAPI_Call(lat: Float, long: Float) : WeatherData {
        val reqString = buildString {
            append("https://api.open-meteo.com/v1/forecast?")
            append("latitude=${lat}&longitude=${long}&")
            //Daily
            append("daily=sunrise,sunset&")
            //Hourly
            append("hourly=precipitation_probability&")
            //Current
            append("current=apparent_temperature,relative_humidity_2m,temperature_2m,weather_code")
            //latitude=38.7167&longitude=-9.1333&daily=sunrise,sunset&hourly=precipitation_probability&
            //current=apparent_temperature,relative_humidity_2m,temperature_2m,weather_code

        }
        val str = reqString.toString()
        val url = URL(reqString.toString())

        url.openStream().use {
            val request = Gson().fromJson(InputStreamReader(it, "UTF-8"),WeatherData::class.java)
            print(request)
            return request
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchWeatherData(lat: Float, long: Float) : Thread {
        return Thread {
            val weather = WeatherAPI_Call(lat,long)
            updateUI(weather)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateUI(request: WeatherData) {
        runOnUiThread {
            // Referências Ids
            val weatherImage : ImageView = findViewById(R.id.weatherIcon)
            val weatherLabel: TextView = findViewById(R.id.weatherCode)

            val temperature : TextView = findViewById(R.id.temperature)
            val apparentTemp : TextView = findViewById(R.id.apparentTempValue)
            val precipitation: TextView = findViewById(R.id.precipitationValue)
            val humidity: TextView = findViewById(R.id.humidityValue)
            val sunrise: TextView = findViewById(R.id.sunriseValue)
            val sunset: TextView = findViewById(R.id.sunsetValue)

            // Preencher valores
            temperature.text = request.current.temperature_2m.toString() +"ºC"
            apparentTemp.text = request.current.apparent_temperature.toString()+"ºC"
            humidity.text = request.current.relative_humidity_2m.toString()+"%"

            //Obter dia da semana e Horas do dia da semana: para sunrise, sunset e precipitation
            val time = LocalDateTime.now() //Horas e data

            val formater1 = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val dayIndex = request.daily.time.indexOf(time.format(formater1))

            val formater2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:00")
            val dateTimeIndex = request.hourly.time.indexOf(time.format(formater2))

            //Preencher sunrise, sunset e precipitation
            sunrise.text = request.daily.sunrise[dayIndex].substring(11,16)
            sunset.text = request.daily.sunset[dayIndex].substring(11,16)
            precipitation.text = request.hourly.precipitation_probability[dateTimeIndex].toString()+"%"


            //Determinar dia, através de sunrise e sunset
            //Obter horas sunset e sunrise
            val sunrise_time = request.daily.sunrise[0].substring(11,16) //Horas entre o indice 11 e 15
            val sunset_time = request.daily.sunset[0].substring(11,16)

            val sunriseTime = LocalTime.parse(sunrise_time, DateTimeFormatter.ofPattern("HH:mm"))
            val sunsetTime = LocalTime.parse(sunset_time, DateTimeFormatter.ofPattern("HH:mm"))

            //determinar se é dia
            val now = LocalTime.now()
            val day = now.isAfter(sunriseTime) && now.isBefore(sunsetTime)


            val mapt = getWeatherCodeMap();
            val wCode = mapt[request.current.weather_code]

            weatherLabel.text = wCode?.label?: "Unknown"

            val wImage = when(wCode) {
                WMO_WeatherCode.CLEAR_SKY,
                WMO_WeatherCode.MAINLY_CLEAR,
                WMO_WeatherCode.PARTLY_CLOUDY->
                    if(day) wCode?.image+"day"
                    else wCode?.image+"night"
                else-> wCode?.image
            }

            val res = resources
            weatherImage.setImageResource(R.drawable.fog)

            val resID = res.getIdentifier(
                wImage,
                "drawable",
                getPackageName()
            )

            val drawable = this.getDrawable(resID)
            weatherImage.setImageDrawable(drawable)

        }
    }


    private fun getCoords():Pair<Float,Float>{

        val latitude: EditText = findViewById(R.id.latitude_coord)
        val longitude: EditText = findViewById(R.id.longitude_coord)

        //Obter o texto
        var latText = latitude.text.toString().toFloat()
        var longText = longitude.text.toString().toFloat()

        //Verificar coords
        if(latText > 90 || latText < -90 || longText > 180 || longText < -180){
            //default Lisboa
            latText =38.76f
            longText =-9.12f
            Toast.makeText(this, "Coords are out of range", Toast.LENGTH_SHORT).show()
        }

        //Colocar o texto
        latitude.setText(latText.toString())
        longitude.setText(longText.toString())

        return Pair(latText,longText)

    }
}