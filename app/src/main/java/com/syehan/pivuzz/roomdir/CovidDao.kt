package com.syehan.pivuzz.roomdir

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CovidDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDaily(dailyRep: DailyRep)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGlobe(globalData: GlobalData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocal(localData: LocalData)

    @Query("Delete from DailyReport")
    suspend fun deleteDaily()

    @Query("Delete from GlobalData")
    suspend fun deleteGlobal()

    @Query("Delete from LocalData")
    suspend fun deleteLocal()

    @Query("Select * from GlobalData where category = :category")
    fun getGlobalItem(category: String): LiveData<List<GlobalData>>

    @Query("Select * from GlobalData")
    fun getGlobal(): LiveData<List<GlobalData>>

    @Query("Select * from LocalData")
    fun getLocal(): LiveData<List<LocalData>>

    @Query("Select Count(category) as itemCount from GlobalData")
    suspend fun getCountGlobal(): Int

    @Query("Select * from DailyReport")
    fun getDaily(): LiveData<List<DailyRep>>


}