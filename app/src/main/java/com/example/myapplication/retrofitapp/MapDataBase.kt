package com.example.myapplication.retrofitapp

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.api.Data

@Database(entities = arrayOf(Data::class), version = 1)
abstract class MapDataBase: RoomDatabase() {
    abstract fun mapDao(): MapDao
}