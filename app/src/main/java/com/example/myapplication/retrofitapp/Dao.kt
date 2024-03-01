package com.example.myapplication.retrofitapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.api.Data

@Dao
interface MapDao {
    @Query("SELECT * FROM MapEntity")
    suspend fun getAllMaps(): MutableList<Data>
    @Query("SELECT * FROM MapEntity where uuid = :uuid")
    suspend fun getMapById(uuid:String): MutableList<Data>
    @Insert
    suspend fun addMap(map:Data)
    @Delete
    suspend fun deleteMap(map:Data)
}