package com.example.apilist.data.network

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.activitatra2.data.model.nueva.Data
import com.example.apilist.data.model.Personatge
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Query("SELECT * FROM StarWarsEntity")
    suspend fun getAllCharacters(): MutableList<Data>

    @Query("SELECT * FROM StarWarsEntity WHERE name = :name")
    suspend fun getCharacterByname(name: String): Personatge?

    @Query("SELECT * FROM StarWarsEntity")
    fun getAllCharactersLiveData(): LiveData<List<Data>>

    @Query("SELECT * FROM StarWarsEntity")
    fun getAllCharactersFlow(): Flow<List<Data>>

    @Query("DELETE FROM StarWarsEntity")
    suspend fun deleteAllCharacters()

    @Insert
    suspend fun addCharacter(character: Data)

    @Delete
    suspend fun deleteCharacter(character: Data)
}
