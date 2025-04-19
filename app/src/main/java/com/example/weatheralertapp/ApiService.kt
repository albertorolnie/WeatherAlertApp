// ApiService.java
package com.example.weatheralertapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("data/2.5/weather")
    fun getWeatherData(
        @Query("q") city: String?,
        @Query("appid") key: String?
    ): Call<com.google.gson.JsonObject?>?

    @GET("data/2.5/air_pollution")
    fun getAirQualityData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") key: String?
    ): Call<com.google.gson.JsonObject?>?
}