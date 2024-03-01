package com.example.myapplication.retrofitapp;

import com.example.myapplication.api.Data
import com.example.myapplication.api.Mapa
import com.example.myapplication.api.ValorantMaps

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path

interface APIInterface {

    @GET("maps")
    suspend fun getMaps(): Response<ValorantMaps>

    @GET("maps/{id}")
    suspend fun getMap(@Path("id")id:String): Response<Mapa>

    companion object {
        val BASE_URL = "https://valorant-api.com/v1/"
        fun create(): APIInterface {
            val client = OkHttpClient.Builder().build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(APIInterface::class.java)
        }
    }

}