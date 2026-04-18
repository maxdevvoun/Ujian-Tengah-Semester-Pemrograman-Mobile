package com.example.uts.ui

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Daftar : Screen("daftar")
    object Result : Screen("result/{name}/{email}/{phone}/{gender}/{topic}") {
        fun createRoute(name: String, email: String, phone: String, gender: String, topic: String) =
            "result/$name/$email/$phone/$gender/$topic"
    }
    object Profile : Screen("profile")
}

data class RegistrationData(
    val name: String,
    val email: String,
    val phone: String,
    val gender: String,
    val topic: String
)
