package com.syehan.pivuzz.roomdir

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CovidDao {

    @Query("Delete from GlobalData")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(global: GlobalData)

    @Query("Update GlobalData Set count = :count Where category = :category")
    suspend fun updateGlobalItem(category: String, count: String)

    @Query("Select * from GlobalData where category = :category")
    suspend fun getGlobalItem(category: String): List<GlobalData>

    @Query("Select Count('category') as itemCount from GlobalData")
    suspend fun getCountGlobal(): CountItem

}