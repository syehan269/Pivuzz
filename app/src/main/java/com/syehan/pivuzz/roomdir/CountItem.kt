package com.syehan.pivuzz.roomdir

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class CountItem(
    @ColumnInfo(name = "itemCount")
    val count: Int?
)