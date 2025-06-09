package com.example.apilist.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.util.CoilUtils.result
import com.example.activitatra2.data.model.nueva.Character
import com.example.activitatra2.data.model.nueva.Data
import com.example.apilist.data.database.Repository
import com.example.apilist.data.model.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class APIViewModel(
    private val settingsRepository: SettingsRepository,
    private val repository: Repository
) : ViewModel() {

    // Variables para el manejo de la UI

    private val _characters = MutableLiveData<Character>()
    val characters: LiveData<Character> get() = _characters

    private val _showLoading = MutableLiveData<Boolean>(true)
    val showLoading: LiveData<Boolean> get() = _showLoading

    private val _searchText = MutableLiveData<String>("")
    val searchText: LiveData<String> get() = _searchText

    private val _selectedOption = MutableStateFlow("List")
    val selectedOption: StateFlow<String> get() = _selectedOption

    private val _favorites = MutableStateFlow<List<Data>>(emptyList())
    val favorites: StateFlow<List<Data>> = _favorites

//    // Funciones para el manejo de la UI
//    fun getCharacters() {
//        CoroutineScope(Dispatchers.IO).launch {
//            val response = repository.getAllCharacters()
//            withContext(Dispatchers.Main) {
//                if (response.isSuccessful) {
//                    _characters.value = response.body()
//                    _showLoading.value = false
//                } else {
//                    Log.e("Error :", response.message())
//                    _showLoading.value = false
//                }
//            }
//        }
//    }

    fun getCharacters() {
        viewModelScope.launch {
            _showLoading.value = true
            try {
                val result = repository.getAllCharacters()
                Log.d("API", "Fetched ${result.body()} characters")
                _characters.value = result.body()
            } catch (e: Exception) {
                Log.e("API", "Error fetching characters", e)
            } finally {
                _showLoading.value = false
            }
        }
    }

    fun guardarFavorito(data: Data) {
        viewModelScope.launch {
            repository.saveAsFavorite(data)
        }
    }

    fun eliminarFavorito(data: Data) {
        viewModelScope.launch {
            repository.deleteFavorite(data)
        }
    }

    suspend fun esFavorito(id: String): Boolean {
        return repository.getFavorites().any { it.id == id }
    }

    fun loadFavorites() {
        viewModelScope.launch {
            _favorites.value = repository.getFavorites().map { data ->
                Data(
                    id = data.id,
                    name = data.name,
                    image = data.image,
                    description = data.description,
                    __v = data.__v,
                )
            }
        }
    }

    // Inicialización de los favoritos
    init {
        viewModelScope.launch {
            repository.getFavoritesFlow().collect { favs ->
                _favorites.value = favs
            }
        }
    }
}
