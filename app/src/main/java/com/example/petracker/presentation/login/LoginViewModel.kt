package com.example.petracker.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petracker.data.AuthRepository
import com.example.petracker.util.Resource
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _signInState = Channel<LoginState>()
    val signInState = _signInState.receiveAsFlow()

    fun loginUser(email: String, password: String) = viewModelScope.launch {
        repository.loginUser(email, password).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _signInState.send(LoginState(isSuccess = "Login Success"))
                }
                is Resource.Loading -> {
                    _signInState.send(LoginState(isLoading = true))
                }
                is Resource.Error -> {
                    _signInState.send(LoginState(isError = result.message))
                }
            }
        }
    }
}