package com.syehan.pivuzz.roomdir

import androidx.annotation.WorkerThread

class CovidRepo(private val covidDao: CovidDao) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertGlobal(globalData: GlobalData){
        covidDao.insert(globalData)
    }

    @WorkerThread
    suspend fun updateGlobal(count: String, category: String){
        covidDao.updateGlobalItem(category, count)
    }

    @WorkerThread
    suspend fun getGlobalItem(category: String){
        covidDao.getGlobalItem(category)
    }

    @WorkerThread
    suspend fun deleteGlobal(){
        covidDao.deleteAll()
    }

    @WorkerThread
    suspend fun getCountGlobal(){
        covidDao.getCountGlobal()
    }

}