package com.syehan.pivuzz.roomdir

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "GlobalData")
data class GlobalData (
    @PrimaryKey
    @ColumnInfo(name = "globalId")
    val globalId: String,
    @ColumnInfo(name ="category")
    val category: String,
    @ColumnInfo(name = "count")
    val count: String
)