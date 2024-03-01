package com.example.myapplication.retrofitapp;

import com.example.myapplication.api.Data

class Repository {

    val apiInterface = APIInterface.create()
    val daoInterfase = MapAplication.database.mapDao()

    suspend fun getAllCharacters() = apiInterface.getMaps()
    suspend fun getAllMap(id:String) = apiInterface.getMap(id)
    suspend fun saveAsFavourite(map: Data) = daoInterfase.addMap(map)
    suspend fun deleteFavourite(map: Data) = daoInterfase.deleteMap(map)
    suspend fun isFavourite(map:Data) = daoInterfase.getMapById(map.uuid).isNotEmpty()
    suspend fun getFavourites() = daoInterfase.getAllMaps()



}