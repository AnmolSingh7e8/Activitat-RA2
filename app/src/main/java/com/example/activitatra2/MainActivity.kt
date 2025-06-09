package com.example.activitatra2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.apilist.data.model.SettingsRepository
import com.example.apilist.ui.navigation.Destinations
import com.example.apilist.ui.navigation.NavigationItem
import com.example.apilist.ui.navigation.NavigationWrapper
import com.example.apilist.ui.theme.ActivitatRA2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ActivitatRA2Theme {
                MyApp()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyApp() {
    var selectedItem: Int by remember { mutableIntStateOf(0) }
    val items = listOf(
        NavigationItem("Home", Icons.Default.Home, Destinations.Pantalla1, 0),
        NavigationItem("Favourites", Icons.Default.Favorite, Destinations.Pantalla2, 1),
        NavigationItem("Settings", Icons.Default.Settings, Destinations.Pantalla3, 2)
    )
    val navController = rememberNavController()
    val context = LocalContext.current
    val settingsRepository = SettingsRepository(context)
    Scaffold(bottomBar = {
        NavigationBar {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = item.index == selectedItem,
                    label = { Text(item.label) },
                    icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                    onClick = {
                        selectedItem = index
                        navController.navigate(item.route)
                    }
                )
            }
        }
    }) {
        NavigationWrapper(navController, settingsRepository = settingsRepository)
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ActivitatRA2Theme {
    }
}