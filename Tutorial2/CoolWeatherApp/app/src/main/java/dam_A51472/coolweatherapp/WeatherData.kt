package dam_A51472.coolweatherapp

data class WeatherData(
    var latitude: String,
    var longitude: String,
    var timezone: String,
    var current: CurrentWeather,
    var hourly: Hourly,
    var daily: Daily
)
data class CurrentWeather (
    var time : String,
    var interval: Int,
    var apparent_temperature :Float,
    var relative_humidity_2m: Int,
    var temperature_2m : Float,
    var weather_code : Int,
)

data class Hourly (
    var time: ArrayList<String>,
    var precipitation_probability:ArrayList<Int>
)

data class Daily (
    var time: ArrayList<String>,
    var sunrise: ArrayList<String>,
    var sunset: ArrayList<String>,
)
/*
enum class WMO_WeatherCode(var code: Int, var image: String) {
    CLEAR_SKY(0,"clear_"),
    MAINLY_CLEAR(1, "mostly_clear_"),
    PARTLY_CLOUDY(2,"partly_cloudy_"),
    OVERCAST(3,"cloudy"),
    FOG(45,"fog"),
    DEPOSITING_RIME_FOG(48,"fog"),
    DRIZZLE_LIGHT(51, "drizzle"),
    DRIZZLE_MODERATE(53, "drizzle"),
    DRIZZLE_DENSE(55,"drizzle"),
    FREEZING_DRIZZLE_LIGHT(56,"freezing_drizzle"),
    FREEZING_DRIZZLE_DENSE(57,"freezing_drizzle"),
    RAIN_SLIGHT(61,"rain_light"),
    RAIN_MODERATE(63,"rain"),
    RAIN_HEAVY(65,"rain_heavy"),
    FREEZING_RAIN_LIGHT(66,"freezing_rain_light"),
    FREEZING_RAIN_HEAVY(67,"freezing_rain_heavy"),
    SNOW_FALL_SLIGHT(71,"snow_light"),
    SNOW_FALL_MODERATE(73,"snow"),
    SNOW_FALL_HEAVY(75,"snow_heavy"),
    SNOW_GRAINS(77, "snow"),
    RAIN_SHOWERS_SLIGHT(80, "rain_light"),
    RAIN_SHOWERS_MODERATE(81, "rain"),
    RAIN_SHOWERS_VIOLENT(82, "rain_heavy"),
    SNOW_SHOWERS_SLIGHT(85, "snow_light"),
    SNOW_SHOWERS_HEAVY(86, "snow_heavy"),
    THUNDERSTORM_SLIGHT_MODERATE(95, "tstorm"),
    THUNDERSTORM_HAIL_SLIGHT(96,"tstorm"),
    THUNDERSTORM_HAIL_HEAVY(99,"tstorm")
}
 */

enum class WMO_WeatherCode(val code: Int, val image: String, val label: String) {
    CLEAR_SKY(0,"clear_","Clear"),
    MAINLY_CLEAR(1, "mostly_clear_","Clear"),
    PARTLY_CLOUDY(2,"partly_cloudy_","Cloudy"),
    OVERCAST(3,"cloudy","Overcast"),
    FOG(45,"fog","Fog"),
    DEPOSITING_RIME_FOG(48,"fog","Fog"),
    DRIZZLE_LIGHT(51, "drizzle","Drizzle"),
    DRIZZLE_MODERATE(53, "drizzle","Drizzle"),
    DRIZZLE_DENSE(55,"drizzle","Drizzle"),
    FREEZING_DRIZZLE_LIGHT(56,"freezing_drizzle","Drizzle"),
    FREEZING_DRIZZLE_DENSE(57,"freezing_drizzle","Drizzle"),
    RAIN_SLIGHT(61,"rain_light","Rain"),
    RAIN_MODERATE(63,"rain","Rain"),
    RAIN_HEAVY(65,"rain_heavy","Rain"),
    FREEZING_RAIN_LIGHT(66,"freezing_rain_light","Rain"),
    FREEZING_RAIN_HEAVY(67,"freezing_rain_heavy","Rain"),
    SNOW_FALL_SLIGHT(71,"snow_light","Snow"),
    SNOW_FALL_MODERATE(73,"snow","Snow"),
    SNOW_FALL_HEAVY(75,"snow_heavy","Snow"),
    SNOW_GRAINS(77, "snow","Snow"),
    RAIN_SHOWERS_SLIGHT(80, "rain_light","Rain showers"),
    RAIN_SHOWERS_MODERATE(81, "rain","Rain showers"),
    RAIN_SHOWERS_VIOLENT(82, "rain_heavy","Rain showers"),
    SNOW_SHOWERS_SLIGHT(85, "snow_light","Snow showers"),
    SNOW_SHOWERS_HEAVY(86, "snow_heavy","Snow showers"),
    THUNDERSTORM_SLIGHT_MODERATE(95, "tstorm","Thunderstorm"),
    THUNDERSTORM_HAIL_SLIGHT(96,"tstorm","Thunderstorm with hail"),
    THUNDERSTORM_HAIL_HEAVY(99,"tstorm","Thunderstorm with hail")
}



fun getWeatherCodeMap() : Map<Int,WMO_WeatherCode> {
    var weatherMap = HashMap<Int,WMO_WeatherCode>()
    WMO_WeatherCode.values().forEach {
        weatherMap.put(it.code,it)
    }
    return weatherMap
}