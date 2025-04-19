// AppDatabase.java
package com.example.weatheralertapp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WeatherAirEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherAirDao(): WeatherAirDao?
}