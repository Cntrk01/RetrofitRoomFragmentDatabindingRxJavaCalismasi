package com.example.retrofitveroomcalismasi4_fragment.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.retrofitveroomcalismasi4_fragment.model.Besin

@Database(entities = arrayOf(Besin::class), version = 1)
abstract class BesinDatabase : RoomDatabase() {
    abstract fun besinDao(): BesinDAO

    companion object
    {
        @Volatile private var instancee : BesinDatabase?=null
        private val lock=Any()
        operator fun invoke(context: Context)= instancee?: synchronized(lock){
            instancee?: databaseOlustur(context).also {
                    instancee=it
            }
        }

        private fun databaseOlustur(context: Context) =
            Room.databaseBuilder(context.applicationContext,BesinDatabase::class.java, "besindatabase").build()
    }


}