package com.example.apilist.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModelStoreOwner
import coil.compose.AsyncImage
import com.example.activitatra2.data.model.nueva.Character
import com.example.activitatra2.data.model.nueva.Data
import com.example.activitatra2.data.model.nueva.Info
import com.example.apilist.data.model.SettingsRepository
import com.example.apilist.viewmodel.APIViewModel
import com.example.apilist.viewmodel.APIViewModelFactory

@Composable
fun Screen1(
    navigateToDetail: (String) -> Unit,
    settingsRepository: SettingsRepository,
    viewModelStoreOwner: ViewModelStoreOwner
) {
    val factory = APIViewModelFactory(settingsRepository)
    val myViewModel: APIViewModel =
        viewModel(viewModelStoreOwner = viewModelStoreOwner, factory = factory)
    val selectedOption by settingsRepository.selectedOption.collectAsState(initial = "List")
    val characters by myViewModel.characters.observeAsState(
        Character(emptyList(), Info(0,"",0,0,0))
    )
    val showLoading: Boolean by myViewModel.showLoading.observeAsState(true)
    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        myViewModel.getCharacters()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Buscar personaje") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )

        if (showLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            val filteredCharacters = characters.data?.filter { character ->
                character.name.contains(searchText, ignoreCase = true)
            } ?: emptyList()

            if (selectedOption == "List") {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredCharacters) { character: Data ->
                        CharacterItem(character) {
                            navigateToDetail(character.name)
                        }
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredCharacters) { character: Data ->
                        CharacterItem(character) {
                            navigateToDetail(character.name)
                        }
                    }
                }
            }


        }
    }
}

@Composable
fun CharacterItem(character: Data, onClick: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = character.image,
                contentDescription = "Imagen del personaje",
                modifier = Modifier
                    .size(90.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = character.name,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
        }
    }
}