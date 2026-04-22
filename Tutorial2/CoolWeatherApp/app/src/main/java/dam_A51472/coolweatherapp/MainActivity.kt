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
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

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
        /*
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
        */

    }

    private fun WeatherAPI_Call(lat: Float, long: Float) : WeatherData {
        val reqString = buildString {
            append("https://api.open-meteo.com/v1/forecast?")
            append("latitude=${lat}&longitude=${long}&")
            //Timezone
            append("timezone=auto&") //Para obter a timezone
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
            val sunriseView: TextView = findViewById(R.id.sunriseValue)
            val sunsetView: TextView = findViewById(R.id.sunsetValue)

            // Preencher valores
            temperature.text = request.current.temperature_2m.toString() +"ºC"
            apparentTemp.text = request.current.apparent_temperature.toString()+"ºC"
            humidity.text = request.current.relative_humidity_2m.toString()+"%"

            //TimeZone
            val zoneId = ZoneId.of(request.timezone)
            val nowZone = ZonedDateTime.now(zoneId)

            //Formatos
            val formaterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val formaterHour = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:00")
            val formaterAll = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
            val formaterTime = DateTimeFormatter.ofPattern("HH:mm")


            //Índices
            val dayIndex = request.daily.time.indexOf(nowZone.format(formaterDate))
            val hourIndex = request.hourly.time.indexOf(nowZone.format(formaterHour))

            //Parse
            val sunriseDateTime = LocalDateTime.parse(request.daily.sunrise[dayIndex], formaterAll)
            val sunsetDateTime = LocalDateTime.parse(request.daily.sunset[dayIndex], formaterAll)

            val sunriseZone = sunriseDateTime.atZone(zoneId) //Ir buscar a data e tempo do sunrise numa dada zona.
            val sunsetZone = sunsetDateTime.atZone(zoneId)

            //Preencher sunrise, sunset e precipitation
            sunriseView.text = sunriseZone.format(formaterTime)
            sunsetView.text = sunsetZone.format(formaterTime)
            precipitation.text = request.hourly.precipitation_probability[hourIndex].toString()+"%"

            //É dia?
            val day = nowZone.isAfter(sunriseZone) && nowZone.isBefore(sunsetZone)

            //Weather Code
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

            //Não funciona
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