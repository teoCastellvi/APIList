package com.example.myapplication.retrofitapp

import android.app.Application
import androidx.room.Room

class MapAplication: Application() {
    companion object {
        lateinit var database: MapDataBase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this,
            MapDataBase::class.java,
            "MapDataBase").build()
    }
}