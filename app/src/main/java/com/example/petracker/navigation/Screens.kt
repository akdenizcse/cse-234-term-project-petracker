package com.example.petracker.navigation

sealed class Screens(val route: String) {
    object LoginScreen : Screens(route = "Login_Screen")
    object SignUpScreen : Screens(route = "SignUp_Screen")

    object HomeScreen: Screens(route = "Home_Screen")
    object PetEntry: Screens(route = "Pet_Entry")

    object PetDetails: Screens(route= "Pet_Details")

}

