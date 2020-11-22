package com.example.practice.models

import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query

interface Interface {

    @GET("data/2.5/weather?lang=ru")

    fun getWeather(
        @Query("q") city: String,
        @Query("appid") appId: String

    ): Call<Weather>

}