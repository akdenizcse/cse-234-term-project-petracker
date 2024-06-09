package com.example.petracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.petracker.presentation.home.HomeScreen
import com.example.petracker.presentation.login.LoginScreen
import com.example.petracker.presentation.pet_details.PetDetailsScreen
import com.example.petracker.presentation.pet_entry.PetEntryScreen
import com.example.petracker.presentation.signup.SignUpScreen

@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screens.LoginScreen.route
    ) {
        composable(route = Screens.LoginScreen.route) {
            LoginScreen(navController = navController)

        }
        composable(route = Screens.SignUpScreen.route) {
            SignUpScreen(navController = navController)
        }
        composable(route = Screens.HomeScreen.route){
            HomeScreen(navController = navController, onItemClick = {petName ->
                navigateToRouteDetails(navController = navController, petName = petName)
            } )
        }
        composable(route = Screens.PetEntry.route){
            PetEntryScreen(navController = navController)
        }
        composable(route = Screens.PetDetails.route){
            navController.previousBackStackEntry?.savedStateHandle?.get<String>("pet")?.let { petName->
                PetDetailsScreen(
                    petName = petName,
                    navController = navController
                )
            }
        }
    }

}

private fun navigateToRouteDetails(navController: NavController, petName: String) {
    navController.currentBackStackEntry?.savedStateHandle?.set("pet", petName)
    navController.navigate(
        route = Screens.PetDetails.route
    )
}