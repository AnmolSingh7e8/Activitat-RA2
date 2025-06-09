package com.example.apilist.ui.navigation
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.apilist.data.database.Repository
import com.example.apilist.data.model.SettingsRepository
import com.example.apilist.ui.screens.DetallScreen
import com.example.apilist.ui.screens.Screen1
import com.example.apilist.ui.screens.Screen2
import com.example.apilist.ui.screens.Screen3
import com.example.apilist.viewmodel.APIViewModel
import com.example.apilist.viewmodel.APIViewModelFactory

@Composable
fun NavigationWrapper(navController: NavHostController, settingsRepository: SettingsRepository) {
    val isDarkModeEnabled by settingsRepository.isDarkModeEnabled.collectAsState(initial = false)

    //Definicion de la navegacion
    NavHost(navController = navController, startDestination = Destinations.Pantalla1) {
        //Pantalla 1
        composable<Destinations.Pantalla1> {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = if (isDarkModeEnabled) Color.Black else Color.White
            ) {
                Screen1(
                    navigateToDetail = { id ->
                        navController.navigate(Destinations.Detall(id))
                    },
                    settingsRepository = settingsRepository,
                    viewModelStoreOwner = navController.context as ViewModelStoreOwner
                )
            }
        }
        //Pantalla 2
        composable<Destinations.Pantalla2> {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = if (isDarkModeEnabled) Color.Black else Color.White
            ) {
                val factory = APIViewModelFactory(
                    settingsRepository = settingsRepository,
                )
                val apiViewModel: APIViewModel = viewModel(factory = factory)

                Screen2(
                    viewModel = apiViewModel,
                    navigateToDetail = { id ->
                        navController.navigate(Destinations.Detall(id.toString()))
                    },
                )
            }
        }
        //Pantalla 3
        composable<Destinations.Pantalla3> {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = if (isDarkModeEnabled) Color.Black else Color.White
            ) {
                Screen3(settingsRepository = settingsRepository)
            }
        }
        //Pantalla Detall
        composable<Destinations.Detall> { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 1
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = if (isDarkModeEnabled) Color.Black else Color.White
            ) {
                DetallScreen(
                    id = id.toString(),
                    navigateBack = {
                        navController.navigate(Destinations.Pantalla1) {
                            popUpTo(Destinations.Pantalla1) {

                                inclusive = true
                            }
                        }
                    },
                    settingsRepository = settingsRepository,
                    viewModelStoreOwner = navController.context as ViewModelStoreOwner
                )
            }
        }
    }
}