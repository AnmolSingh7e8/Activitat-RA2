package com.example.apilist.data.database

import com.example.activitatra2.data.model.nueva.Data
import com.example.activitatra2.data.network.CharacterApplication
import com.example.apilist.data.model.Item
import com.example.apilist.data.network.ApiInterface
import kotlinx.coroutines.flow.Flow

class Repository {
    //API functions
    val apiInterface = ApiInterface.create()
    suspend fun getAllCharacters() = apiInterface.getData()

    val daoInterface = CharacterApplication.database.characterDao()
    private val favorites = mutableListOf<Data>()

    //Database functions
    suspend fun saveAsFavorite(character: Data) = daoInterface.addCharacter(character)
    suspend fun deleteFavorite(character: Data) = daoInterface.deleteCharacter(character)
    suspend fun isFavorite(characterId: Int) = daoInterface.getCharacterById(characterId)
    suspend fun getFavorites() = daoInterface.getAllCharacters()

    fun getFavoritesFlow(): Flow<List<Data>> {
        return daoInterface.getAllCharactersFlow() // Correct reference to daoInterface
    }

}
