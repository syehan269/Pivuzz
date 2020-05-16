package com.syehan.pivuzz.model


import com.google.gson.annotations.SerializedName

data class DateItem(
    @SerializedName("confirmed")
    val confirmed: String,
    @SerializedName("countryRegion")
    val countryRegion: String,
    @SerializedName("deaths")
    val deaths: String,
    @SerializedName("lastUpdate")
    val lastUpdate: String,
    @SerializedName("provinceState")
    val provinceState: String,
    @SerializedName("recovered")
    val recovered: String
)