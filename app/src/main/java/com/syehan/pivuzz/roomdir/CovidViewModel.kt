package com.syehan.pivuzz.roomdir

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

public class CovidViewModel(application: Application): AndroidViewModel(application) {

    val repo: CovidRepo

    init{
        val covidDao = CovidDb.getDb(application, viewModelScope).covidDao()
        repo = CovidRepo(covidDao)
    }

    fun insertGlobal(globalData: GlobalData) = viewModelScope.launch(Dispatchers.IO){
        repo.insertGlobal(globalData)
    }

    fun updateGlobal(category: String, count: String) = viewModelScope.launch(Dispatchers.IO){
        repo.updateGlobal(count, category)
    }

    fun getGlobalItem(category: String) = viewModelScope.launch(Dispatchers.IO){
        repo.getGlobalItem(category)
    }

    fun deleteGlobal() = viewModelScope.launch(Dispatchers.IO){
        repo.deleteGlobal()
    }

    fun getCountGlobal() = viewModelScope.launch(Dispatchers.IO){
        repo.getCountGlobal()
    }

}