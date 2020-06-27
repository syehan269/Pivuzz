package com.syehan.pivuzz.roomdir

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class CovidRepo(private val covidDao: CovidDao) {

    val allDailyRep: LiveData<List<DailyRep>> = covidDao.getDaily()
    val allGlobe: LiveData<List<GlobalData>> = covidDao.getGlobal()
    val allLocal: LiveData<List<LocalData>> = covidDao.getLocal()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertDaily(dailyRep: DailyRep){
        covidDao.insertDaily(dailyRep)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertGlobal(globalData: GlobalData){
        covidDao.insertGlobe(globalData)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertLocal(localData: LocalData){
        covidDao.insertLocal(localData)
    }

    @WorkerThread
    fun getItemGlo(category: String) {
        //return covidDao.getGlobalItem(category)
    }

    @WorkerThread
    suspend fun deleteGlobal(){
        covidDao.deleteGlobal()
    }

    @WorkerThread
    suspend fun deleteDaily(){
        covidDao.deleteDaily()
    }

    @WorkerThread
    suspend fun deleteLocal(){
        covidDao.deleteLocal()
    }
}