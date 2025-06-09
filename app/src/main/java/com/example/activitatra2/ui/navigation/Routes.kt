package com.example.apilist.ui.navigation

import kotlinx.serialization.Serializable

//Routes para la navegació de la app
sealed class Destinations {
    @Serializable
    object Pantalla1 : Destinations()

    @Serializable
    object Pantalla2 : Destinations()

    @Serializable
    object Pantalla3 : Destinations()

    @Serializable
    data class Detall(val id: String)

}
