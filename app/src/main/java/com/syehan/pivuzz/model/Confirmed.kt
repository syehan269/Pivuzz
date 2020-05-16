package com.syehan.pivuzz.model


import com.google.gson.annotations.SerializedName

data class Confirmed(
    @SerializedName("value")
    val value: Int
)