package com.syehan.pivuzz.api

import com.syehan.pivuzz.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiPoint {

    @GET
    fun getMainInfo(): Call<CovMain>

    @GET("daily")
    fun getListDaily(): Call<List<DailyReportItem>>

    @GET("daily/{date}")
    fun getDateReport(@Path("date") date: String): Call<List<DateItem>>

    @GET("countries")
    fun getCountry(): Call<Count>

}