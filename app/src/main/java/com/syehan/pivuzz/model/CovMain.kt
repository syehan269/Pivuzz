package com.syehan.pivuzz.model


import com.google.gson.annotations.SerializedName

data class CovMain(
    @SerializedName("confirmed")
    val confirmed: Confirmed,
    @SerializedName("deaths")
    val deaths: Deaths,
    @SerializedName("lastUpdate")
    val lastUpdate: String,
    @SerializedName("recovered")
    val recovered: Recovered
)