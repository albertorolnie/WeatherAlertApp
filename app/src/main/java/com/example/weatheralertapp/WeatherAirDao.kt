// WeatherAirDao.java
package com.example.weatheralertapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WeatherAirDao {
    @Insert
    fun insert(data: WeatherAirEntity?)

    @get:Query("SELECT * FROM weather_air_data ORDER BY timestamp DESC LIMIT 100")
    val recentData: List<Any?>?
}