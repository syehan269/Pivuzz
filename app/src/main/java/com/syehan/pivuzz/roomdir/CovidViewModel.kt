package com.syehan.pivuzz.roomdir

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

public class CovidViewModel(application: Application): AndroidViewModel(application) {

    val repo: CovidRepo
    val allGlobal: LiveData<List<GlobalData>>
    val allDailyRep: LiveData<List<DailyRep>>
    val allLocal: LiveData<List<LocalData>>

    init{
        val covidDao = CovidDb.getDb(application, viewModelScope).covidDao()
        repo = CovidRepo(covidDao)
        allGlobal = repo.allGlobe
        allDailyRep = repo.allDailyRep
        allLocal = repo.allLocal
    }

    fun insertDaily(dailyRep: DailyRep) = viewModelScope.launch(Dispatchers.IO){
        repo.insertDaily(dailyRep)
    }

    fun insertGlobal(globalData: GlobalData) = viewModelScope.launch(Dispatchers.IO){
        repo.insertGlobal(globalData)
    }

    fun insertLocal(localData: LocalData) = viewModelScope.launch(Dispatchers.IO){
        repo.insertLocal(localData)
    }

    fun getGlobalItem(category: String) = viewModelScope.launch(Dispatchers.IO){
        //repo.getItemGlo(category)
    }

    fun deleteDaily() = viewModelScope.launch(Dispatchers.IO){
        repo.deleteDaily()
    }

    fun deleteGlobe() = viewModelScope.launch(Dispatchers.IO){
        repo.deleteGlobal()
    }

    fun deleteLocal() = viewModelScope.launch(Dispatchers.IO){
        repo.deleteLocal()
    }

    fun getCountGlobal() = viewModelScope.launch(Dispatchers.IO){
        //repo.getCountGlobal()
    }

}