package com.example.apilist.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CharacterEntity")

data class Item(
    @PrimaryKey (autoGenerate = true)
    val id: Int = 0,
    val createdAt: String,
    val gender: String,
    val image: String,
    val name: String,
    val species: String,
    val status: String
)