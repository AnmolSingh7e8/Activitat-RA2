package com.example.activitatra2.data.model.nueva

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "StarWarsEntity")

data class Data(
    @SerializedName("_id")
    val id: String = "",
    @PrimaryKey (autoGenerate = true)
    val __v: Int,
    val description: String,
    val image: String,
    val name: String
)