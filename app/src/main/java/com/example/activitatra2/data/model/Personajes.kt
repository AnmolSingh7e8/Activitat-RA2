package com.example.apilist.data.model

data class Personajes(
    val items: List<Item>,
    val page: Int,
    val pages: Int,
    val size: Int,
    val total: Int
)