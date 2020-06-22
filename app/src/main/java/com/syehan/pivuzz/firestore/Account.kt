package com.syehan.pivuzz.firestore

data class Account(
    val uid: String? = null,
    val email: String? = null,
    val password: String? = null,
    val nation: String? = null,
    val nationId: Int? = null
)