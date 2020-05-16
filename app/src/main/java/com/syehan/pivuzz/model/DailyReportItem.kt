package com.syehan.pivuzz.model


import com.google.gson.annotations.SerializedName

data class DailyReportItem(
    @SerializedName("active")
    val active: Int,
    @SerializedName("deaths")
    val deaths: DeathsX,
    @SerializedName("deltaConfirmed")
    val deltaConfirmed: Int,
    @SerializedName("deltaRecovered")
    val deltaRecovered: Int,
    @SerializedName("incidentRate")
    val incidentRate: Double,
    @SerializedName("mainlandChina")
    val mainlandChina: Int,
    @SerializedName("otherLocations")
    val otherLocations: Int,
    @SerializedName("peopleTested")
    val peopleTested: Int,
    @SerializedName("reportDate")
    val reportDate: String,
    @SerializedName("totalConfirmed")
    val totalConfirmed: Int,
    @SerializedName("totalRecovered")
    val totalRecovered: Int
)