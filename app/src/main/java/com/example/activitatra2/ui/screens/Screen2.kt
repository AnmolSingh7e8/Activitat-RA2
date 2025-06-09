package com.example.apilist.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apilist.viewmodel.APIViewModel

@Composable
fun Screen2(viewModel: APIViewModel, navigateToDetail: (String) -> Unit) {
    val favorites by viewModel.favorites.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.loadFavorites()
    }
    // Composable para mostrar la lista de favoritos
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Items en la lista de favoritos
        items(favorites) { character ->
            CharacterItem(character) {
                navigateToDetail(character.id)
            }
        }
    }
}