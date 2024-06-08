package com.example.petracker.presentation.signup

data class SignUpState(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = ""
)
