package com.example.apilist.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorites")
data class Personatge(
    @PrimaryKey val id: String,
    val name: String,
    val affiliation: String?,
    val species: String?,
    val gender: String?,
    val image: String?
)