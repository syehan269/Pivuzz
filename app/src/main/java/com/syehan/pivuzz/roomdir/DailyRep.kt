package com.syehan.pivuzz.roomdir

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.syehan.pivuzz.model.Confirmed
import com.syehan.pivuzz.model.Deaths
import com.syehan.pivuzz.model.DeathsX
import com.syehan.pivuzz.model.Recovered

@Entity(tableName = "DailyReport")
data class DailyRep(
    @PrimaryKey
    @ColumnInfo(name="reportDate")
    val reportDate: String,
    @ColumnInfo(name="totalConfirmed")
    val totalConfirmed: Int,
    @ColumnInfo(name="totalRecovered")
    val totalRecovered: Int,
    @ColumnInfo(name="deaths")
    val deaths: Int
)