package com.syehan.pivuzz

import android.content.Context
import android.content.SharedPreferences

class AccountPreference internal constructor(val context: Context){

    private val preference = "AccountPreference"
    private val sharedPreference: SharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE)

    fun save(key_name: String, value: String){
        val editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.putString(key_name, value)
        editor.apply()
    }

    fun save(key_name: String, value: Boolean){
        val editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.putBoolean(key_name, value)
        editor.apply()
    }

    fun save(key_name: String, value: Int){
        val editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.putInt(key_name, value)
        editor.apply()
    }

    fun getInt(key_name: String):Int?{
        return sharedPreference.getInt(key_name, 0)
    }

    fun getString(key_name: String):String?{
        return sharedPreference.getString(key_name, "empty")
    }

    fun getBoolean(key_name: String):Boolean{
        return sharedPreference.getBoolean(key_name, false)
    }

    fun clearPreference(){
        val editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.clear()
        editor.apply()
    }

    fun removeValue(key_name: String){
        val editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.remove(key_name)
        editor.apply()
    }

}
