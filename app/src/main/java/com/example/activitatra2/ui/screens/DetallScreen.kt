package com.example.activitatra2.ui.screens

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
import com.example.activitatra2.data.model.nueva.Data
import com.example.apilist.data.model.Personatge
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

    var character by remember { mutableStateOf<Personatge?>(null) }
    var isFavorite by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(id) {
        viewModel.getCharacters()
    }

    if (character == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Aquest personatge no està desat a favorits", color = MaterialTheme.colorScheme.error)
        }
        return
    }

    // CONTINGUT DETALL
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            // Icon favorit
            Icon(
                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Toggle Favorite",
                tint = if (isFavorite) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.End)
                    .padding(16.dp)
                    .clickable {
                        coroutineScope.launch {
                            if (isFavorite) {
                                viewModel.eliminarFavorito(
                                    Data(
                                        id = character!!.id,
                                        name = character!!.name,
                                        image = character!!.image ?: "",
                                        description = character!!.affiliation ?: "Sense descripció",
                                        __v = 0
                                    )
                                )
                            } else {
                                viewModel.guardarFavorito(
                                    Data(
                                        id = character!!.id,
                                        name = character!!.name,
                                        image = character!!.image ?: "",
                                        description = character!!.affiliation ?: "Sense descripció",
                                        __v = 0
                                    )
                                )
                            }
                            isFavorite = !isFavorite
                        }
                    }
            )

            AsyncImage(
                model = character?.image,
                contentDescription = "Imatge del personatge",
                modifier = Modifier
                    .size(250.dp)
                    .padding(16.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = character!!.name,
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
                Text("Tornar", fontSize = 18.sp)
            }
        }
    }
}
