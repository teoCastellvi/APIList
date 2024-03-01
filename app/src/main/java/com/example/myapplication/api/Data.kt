package com.example.myapplication.api

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MapEntity")
data class Data(
    val coordinates: String,
    val displayIcon: String,
    val listViewIcon: String,
    val displayName: String,
    val narrativeDescription: String,
    val splash: String,
    val tacticalDescription: String,
    @PrimaryKey
    val uuid: String,
)