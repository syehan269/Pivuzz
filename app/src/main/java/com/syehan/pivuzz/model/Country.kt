package com.syehan.pivuzz.model

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name")
    val name: String
)