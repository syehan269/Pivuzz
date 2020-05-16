package com.syehan.pivuzz.model


import com.google.gson.annotations.SerializedName

data class Local(
    @SerializedName("confirmed")
    val confirmed: ConfirmedX,
    @SerializedName("deaths")
    val deaths: DeathsXX,
    @SerializedName("lastUpdate")
    val lastUpdate: String,
    @SerializedName("recovered")
    val recovered: RecoveredX
)