package com.example.weatheralertapp

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.google.gson.JsonObject
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private var db: AppDatabase? = null
    private var apiService: ApiService? = null

    // UI
    private lateinit var tvCity: TextView
    private lateinit var tvTemp: TextView
    private lateinit var tvHumidity: TextView
    private lateinit var tvDesc: TextView
    private lateinit var tvAQI: TextView
    private lateinit var btnRefresh: Button
    private lateinit var btnGraph: Button

    private val city = "Nicosia"
    private val apiKey = "cd22f316e029d977a08abe5cac95eb28"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar UI
        tvCity = findViewById(R.id.tvCity)
        tvTemp = findViewById(R.id.tvTemp)
        tvHumidity = findViewById(R.id.tvHumidity)
        tvDesc = findViewById(R.id.tvDesc)
        tvAQI = findViewById(R.id.tvAQI)
        btnRefresh = findViewById(R.id.btnRefresh)
        btnGraph = findViewById(R.id.btnGraph)

        // Room DB
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "weather.db")
            .allowMainThreadQueries().build()

        // Retrofit
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        // Acciones
        btnRefresh.setOnClickListener {
            fetchWeatherAndAirData(city)
        }

        btnGraph.setOnClickListener {
            val intent = Intent(this, ChartActivity::class.java)
            startActivity(intent)
        }

        // Primer llamada al abrir la app
        fetchWeatherAndAirData(city)
    }

    private fun fetchWeatherAndAirData(city: String) {
        apiService?.getWeatherData(city, apiKey)
            ?.enqueue(object : Callback<JsonObject?> {
                override fun onResponse(
                    call: Call<JsonObject?>,
                    response: Response<JsonObject?>
                ) {
                    val weatherObj = response.body()
                    if (weatherObj != null) {
                        val temp = weatherObj.getAsJsonObject("main").get("temp").asFloat - 273.15f // Kelvin -> Celsius
                        val humidity = weatherObj.getAsJsonObject("main").get("humidity").asFloat
                        val lat = weatherObj.getAsJsonObject("coord").get("lat").asDouble
                        val lon = weatherObj.getAsJsonObject("coord").get("lon").asDouble
                        val desc = weatherObj.getAsJsonArray("weather")
                            .get(0).asJsonObject
                            .get("description").asString

                        apiService?.getAirQualityData(lat, lon, apiKey)
                            ?.enqueue(object : Callback<JsonObject?> {
                                @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
                                override fun onResponse(
                                    call: Call<JsonObject?>,
                                    response: Response<JsonObject?>
                                ) {
                                    val aqi = response.body()
                                        ?.getAsJsonArray("list")
                                        ?.get(0)?.asJsonObject
                                        ?.getAsJsonObject("main")
                                        ?.get("aqi")?.asFloat ?: 0F

                                    val entity = WeatherAirEntity().apply {
                                        this.city = city
                                        this.temperature = temp
                                        this.humidity = humidity
                                        this.description = desc
                                        this.aqi = aqi
                                        this.timestamp = System.currentTimeMillis()
                                    }

                                    db?.weatherAirDao()?.insert(entity)

                                    updateUI(entity)

                                    if (aqi > 3) {
                                        val sender = BluetoothSender()
                                        sender.sendData("ALERTA: AQI elevado en $city")
                                    }
                                }

                                override fun onFailure(call: Call<JsonObject?>, t: Throwable) {
                                    // Error al obtener calidad del aire
                                }
                            })
                    }
                }

                override fun onFailure(call: Call<JsonObject?>, t: Throwable) {
                    // Error al obtener datos del clima
                }
            })
    }

    private fun updateUI(entity: WeatherAirEntity) {
        tvCity.text = "Ciudad: ${entity.city}"
        tvTemp.text = "Temperatura: ${"%.1f".format(entity.temperature)}°C"
        tvHumidity.text = "Humedad: ${entity.humidity}%"
        tvDesc.text = "Descripción: ${entity.description}"
        tvAQI.text = "AQI: ${entity.aqi.toInt()}"
    }
}
