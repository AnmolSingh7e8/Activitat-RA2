package com.example.apilist.data.model

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.core.content.edit
import com.example.apilist.data.network.AppDatabase
import com.example.apilist.data.network.CharacterDao

class SettingsRepository(context: Context) {


    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)

    fun <T> saveSettingValue(key: String, value: T) {
        with(sharedPreferences.edit()) {
            when (value) {
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                is Long -> putLong(key, value)
                else -> throw IllegalArgumentException("Unsupported type")
            }
            apply()
        }
    }

    fun <T> getSettingValue(key: String, defaultValue: T): T {
        return when (defaultValue) {
            is String -> sharedPreferences.getString(key, defaultValue) as T
            is Int -> sharedPreferences.getInt(key, defaultValue) as T
            is Boolean -> sharedPreferences.getBoolean(key, defaultValue) as T
            is Float -> sharedPreferences.getFloat(key, defaultValue) as T
            is Long -> sharedPreferences.getLong(key, defaultValue) as T
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }

    private val _isDarkModeEnabled = MutableStateFlow(false)
    val isDarkModeEnabled: StateFlow<Boolean> get() = _isDarkModeEnabled

    private val _selectedOption = MutableStateFlow("List") // "List" o "Grid"
    val selectedOption: StateFlow<String> get() = _selectedOption

    fun setSelectedOption(option: String) {
        _selectedOption.value = option
    }

    fun setDarkModeEnabled(enabled: Boolean) {
        _isDarkModeEnabled.value = enabled
    }

    fun getFavorites(): List<Item> {
        val favoritesJson = sharedPreferences.getString("favorites", "[]")
        return Gson().fromJson(favoritesJson, Array<Item>::class.java).toList()
    }

    suspend fun eliminarLista(context: Context) {
        val db = AppDatabase.getInstance(context)
        db.characterDao().deleteAllCharacters()
    }

    fun addFavorite(item: Item) {
        val currentFavorites = getFavorites().toMutableList()
        currentFavorites.add(item)
        val updatedJson = Gson().toJson(currentFavorites)
        sharedPreferences.edit() { putString("favorites", updatedJson) }
    }

    fun eliminarFavorite(item: Item) {
        val currentFavorites = getFavorites().toMutableList()
        currentFavorites.removeAll { it.id == item.id }
        val updatedJson = Gson().toJson(currentFavorites)
        sharedPreferences.edit { putString("favorites", updatedJson) }
    }

//    fun eliminarLista() {
//        sharedPreferences.edit { remove("favorites") }
//    }
}