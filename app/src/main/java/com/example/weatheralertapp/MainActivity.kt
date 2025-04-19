/*
 * Nombre del proyecto: WeatherAlertApp
 * Plataforma: Android Studio (Kotlin)
 */
// MainActivity.java
package com.example.weatheralertapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private var db: AppDatabase? = null
    private var apiService: ApiService? = null

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase::class.java, "weather.db")
            .allowMainThreadQueries().build()

        val retrofit: Retrofit = Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(ApiService::class.java)

        val city = "Nicosia"
        fetchWeatherAndAirData(city)
    }

    private fun fetchWeatherAndAirData(city: String) {
        apiService.getWeatherData(city, "YOUR_API_KEY")
            .enqueue(object : Callback<com.google.gson.JsonObject?>() {
                override fun onResponse(
                    call: Call<com.google.gson.JsonObject?>?,
                    response: Response<com.google.gson.JsonObject?>
                ) {
                    val weatherObj: com.google.gson.JsonObject = response.body()
                    val temp: Float = weatherObj.getAsJsonObject("main").get("temp").getAsFloat()
                    val humidity: Float =
                        weatherObj.getAsJsonObject("main").get("humidity").getAsFloat()
                    val lat: Double = weatherObj.getAsJsonObject("coord").get("lat").getAsDouble()
                    val lon: Double = weatherObj.getAsJsonObject("coord").get("lon").getAsDouble()

                    apiService.getAirQualityData(lat, lon, "YOUR_API_KEY")
                        .enqueue(object : Callback<com.google.gson.JsonObject?>() {
                            override fun onResponse(
                                call: Call<com.google.gson.JsonObject?>?,
                                response: Response<com.google.gson.JsonObject?>
                            ) {
                                val aqi: Float = response.body().getAsJsonArray("list")
                                    .get(0).getAsJsonObject()
                                    .getAsJsonObject("main")
                                    .get("aqi").getAsFloat()

                                val entity: WeatherAirEntity = WeatherAirEntity()
                                entity.city = city
                                entity.temperature = temp
                                entity.humidity = humidity
                                entity.aqi = aqi
                                entity.description =
                                    weatherObj.getAsJsonArray("weather").get(0).getAsJsonObject()
                                        .get("description").getAsString()
                                entity.timestamp = System.currentTimeMillis()
                                db.weatherAirDao().insert(entity)

                                if (aqi > 3) {
                                    val sender: BluetoothSender = BluetoothSender()
                                    sender.sendData("ALERTA: AQI elevado en $city")
                                }
                            }

                            override fun onFailure(
                                call: Call<com.google.gson.JsonObject?>?,
                                t: Throwable?
                            ) {
                            }
                        })
                }

                override fun onFailure(call: Call<com.google.gson.JsonObject?>?, t: Throwable?) {}
            })
    }
}