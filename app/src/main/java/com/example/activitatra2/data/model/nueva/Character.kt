package com.example.activitatra2.data.model.nueva

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("data")
    val data: List<Data>?,
    @SerializedName("info")
    val info: Info
)