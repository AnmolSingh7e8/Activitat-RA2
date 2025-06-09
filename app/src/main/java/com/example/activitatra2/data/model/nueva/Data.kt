package com.example.activitatra2.data.model.nueva

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "StarWarsEntity")

data class Data(
    @PrimaryKey (autoGenerate = true)
    @SerializedName("_id")
    val id: Int = 0,
    val __v: Int,
    val description: String,
    val image: String,
    val name: String
)