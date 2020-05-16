package com.syehan.pivuzz.api

import com.syehan.pivuzz.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiPoint {

    @GET("api")
    fun getMainInfo(): Call<CovMain>

    @GET("api/daily")
    fun getListDaily(): Call<List<DailyReportItem>>

    @GET("api/daily/{date}")
    fun getDateReport(@Path("date") date: String): Call<List<DateItem>>

    @GET("api/countries")
    fun getCountry(): Call<Count>

    @GET("api/countries/{country}")
    fun getLocal(@Path("country") country: String): Call<Local>

}