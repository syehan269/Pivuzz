package com.syehan.pivuzz.firestore

data class Global(
    val confirmed: Int? = null,
    val confirmNotif: String? = null,
    val death: Int? = null,
    val deathNotif: String? = null,
    val recovered: Int? = null,
    val recoveredNotif: String? = null
)