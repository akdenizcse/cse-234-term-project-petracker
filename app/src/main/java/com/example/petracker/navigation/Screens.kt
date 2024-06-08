package com.example.petracker.navigation

sealed class Screens(val route: String) {
    object LoginScreen : Screens(route = "Login_Screen")
    object SignUpScreen : Screens(route = "SignUp_Screen")

    object HomeScreen: Screens(route = "Home_Screen")
}