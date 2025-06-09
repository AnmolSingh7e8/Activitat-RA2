package com.example.activitatra2.data.model.nueva

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("items")
    val data: List<Data>?,
    val info: Info
)