package com.syehan.pivuzz.model


import com.google.gson.annotations.SerializedName

data class DeathsX(
    @SerializedName("china")
    val china: Int,
    @SerializedName("outsideChina")
    val outsideChina: Int,
    @SerializedName("total")
    val total: Int
)