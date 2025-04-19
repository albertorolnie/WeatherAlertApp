// WeatherAirEntity.java
package com.example.weatheralertapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_air_data")
class WeatherAirEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var city: String? = null
    var temperature: Float = 0f
    var humidity: Float = 0f
    var aqi: Float = 0f
    var description: String? = null
    var timestamp: Long = 0
}