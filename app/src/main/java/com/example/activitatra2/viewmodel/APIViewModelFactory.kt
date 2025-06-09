package com.example.apilist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apilist.data.database.Repository
import com.example.apilist.data.model.SettingsRepository

class APIViewModelFactory(
    private val settingsRepository: SettingsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(APIViewModel::class.java)) {
            return APIViewModel(
                settingsRepository,
                repository = Repository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}