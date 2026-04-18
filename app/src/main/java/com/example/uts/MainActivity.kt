package com.example.uts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.uts.ui.*
import com.example.uts.ui.theme.DeepBlue
import com.example.uts.ui.theme.UTSTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UTSTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val items = listOf(
        Triple(Screen.Home, "Home", Icons.Default.Home),
        Triple(Screen.Daftar, "Daftar", Icons.Default.AppRegistration),
        Triple(Screen.Profile, "Profile", Icons.Default.AccountCircle)
    )

    val showBottomBar = currentDestination?.route in listOf(
        Screen.Home.route,
        Screen.Daftar.route,
        Screen.Profile.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = Color.White,
                    contentColor = DeepBlue
                ) {
                    items.forEach { (screen, label, icon) ->
                        NavigationBarItem(
                            icon = { Icon(icon, contentDescription = label) },
                            label = { Text(label) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = DeepBlue,
                                selectedTextColor = DeepBlue,
                                unselectedIconColor = Color.Gray,
                                unselectedTextColor = Color.Gray,
                                indicatorColor = Color.LightGray.copy(alpha = 0.3f)
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Login.route) { LoginScreen(navController) }
            composable(Screen.Register.route) { RegisterScreen(navController) }
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(Screen.Daftar.route) { DaftarScreen(navController) }
            composable(
                route = Screen.Result.route,
                arguments = listOf(
                    navArgument("name") { type = NavType.StringType },
                    navArgument("email") { type = NavType.StringType },
                    navArgument("phone") { type = NavType.StringType },
                    navArgument("gender") { type = NavType.StringType },
                    navArgument("topic") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val name = backStackEntry.arguments?.getString("name") ?: ""
                val email = backStackEntry.arguments?.getString("email") ?: ""
                val phone = backStackEntry.arguments?.getString("phone") ?: ""
                val gender = backStackEntry.arguments?.getString("gender") ?: ""
                val topic = backStackEntry.arguments?.getString("topic") ?: ""
                
                ResultScreen(
                    navController = navController,
                    data = RegistrationData(name, email, phone, gender, topic)
                )
            }
            composable(Screen.Profile.route) { ProfileScreen(navController) }
        }
    }
}
