package com.syehan.pivuzz.roomdir

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LocalData")
data class LocalData (
    @PrimaryKey
    @ColumnInfo(name = "localId")
    val globalId: String,
    @ColumnInfo(name ="category")
    val category: String,
    @ColumnInfo(name = "count")
    val count: String
)