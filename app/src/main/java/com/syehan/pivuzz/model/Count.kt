package com.syehan.pivuzz.model


import com.google.gson.annotations.SerializedName

data class Count(
    @SerializedName("countries")
    val countries: List<Country>
)