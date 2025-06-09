package com.example.apilist.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.activitatra2.data.model.nueva.Info
import com.example.apilist.data.model.SettingsRepository
import com.example.apilist.viewmodel.APIViewModel
import com.example.apilist.viewmodel.APIViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun DetallScreen(
    id: String,
    navigateBack: () -> Unit,
    settingsRepository: SettingsRepository,
    viewModelStoreOwner: ViewModelStoreOwner
) {
    val factory = APIViewModelFactory(settingsRepository)
    val viewModel: APIViewModel =
        viewModel(viewModelStoreOwner = viewModelStoreOwner, factory = factory)

    val characters by viewModel.characters.observeAsState(
        com.example.activitatra2.data.model.nueva.Character(emptyList(), Info(0, "", 0, 0, 0))
    )

    var isFavorite by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(id) {
        viewModel.getCharacters()
        isFavorite = viewModel.esFavorito(id.toString())
    }

    if (characters.data?.isEmpty() ?: true) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    } else {
        val character = characters.data?.find { it.id == id }

        if (character != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .background(MaterialTheme.colorScheme.background),
                ) {
                    // Icono favorito con click para guardar/eliminar
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Toggle Favorite",
                        tint = if (isFavorite) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.End)
                            .clickable {
                                isFavorite = !isFavorite
                                coroutineScope.launch {
                                    if (isFavorite) {
                                        viewModel.guardarFavorito(character)
                                    } else {
                                        viewModel.eliminarFavorito(character)
                                    }
                                }
                            }
                    )

                    AsyncImage(
                        model = character.image,
                        contentDescription = "Character Image",
                        modifier = Modifier
                            .size(250.dp)
                            .padding(16.dp),
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        text = character.name,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Button(
                        onClick = navigateBack,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .width(250.dp)
                            .height(50.dp)
                    ) {
                        Text("Return", fontSize = 18.sp)
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Character not found",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
