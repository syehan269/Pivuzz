package com.syehan.pivuzz.roomdir

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = [GlobalData::class], version = 1, exportSchema = false)
public abstract class CovidDb : RoomDatabase() {

    abstract fun covidDao(): CovidDao

    companion object{
        @Volatile
        private var roomInstance: CovidDb? = null

        fun getDb(context: Context, scope: CoroutineScope): CovidDb{
            val tempInstance = roomInstance
            if (tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CovidDb::class.java,
                    "CovidDb"
                ).build()
                roomInstance = instance
                return instance
            }

        }
    }

}