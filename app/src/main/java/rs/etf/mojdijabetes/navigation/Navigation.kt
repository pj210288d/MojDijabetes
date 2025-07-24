package rs.etf.mojdijabetes.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import rs.etf.mojdijabetes.screens.home.HomeScreen
import rs.etf.mojdijabetes.screens.login.LoginScreen
import rs.etf.mojdijabetes.screens.profile.ProfileScreen
import rs.etf.mojdijabetes.ui.viewmodel.HomeViewModel

@Composable
fun MojDijabetesApp() {
    val navController = rememberNavController()
    val homeViewModel: HomeViewModel = hiltViewModel()

    MaterialTheme {
        NavHost(
            navController = navController,
            startDestination = "login"
        ) {
            composable("login") {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                )
            }
            composable("home") {
                HomeScreen(
                    onNavigateToProfile = { navController.navigate("profile") },
                    navController = navController

                )
            }
            composable("profile") {
                ProfileScreen(
                    onNavigateBack = { navController.navigateUp() },
                    onSignOut = {
                        homeViewModel.signOut()
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}