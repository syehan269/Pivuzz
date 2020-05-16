package com.syehan.pivuzz.model


import com.google.gson.annotations.SerializedName

data class Deaths(
    @SerializedName("value")
    val value: Int
)