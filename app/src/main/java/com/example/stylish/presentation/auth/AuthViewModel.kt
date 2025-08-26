package com.example.stylish.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stylish.domain.usecase.LoginUseCase
import com.example.stylish.domain.usecase.SignUpUseCase
import com.example.stylish.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow<Result<String>>(Result.Idle)
    val authState: StateFlow<Result<String>> = _authState

    fun login(email: String, password: String) {
        // Validate inputs before proceeding
        if (email.isBlank() || password.isBlank()) {
            _authState.value = Result.Failure("Email and password cannot be empty")
            return
        }

        _authState.value = Result.Loading
        viewModelScope.launch {
            try {
                val result = loginUseCase(email, password)
                _authState.value = result
            } catch (e: Exception) {
                _authState.value = Result.Failure(e.message ?: "Login failed")
            }
        }
    }

    fun signup(email: String, password: String) {
        // Validate inputs before proceeding
        if (email.isBlank() || password.isBlank()) {
            _authState.value = Result.Failure("Email and password cannot be empty")
            return
        }

        _authState.value = Result.Loading
        viewModelScope.launch {
            try {
                val result = signUpUseCase(email, password)
                _authState.value = result
            } catch (e: Exception) {
                _authState.value = Result.Failure(e.message ?: "Signup failed")
            }
        }
    }

    fun resetState() {
        _authState.value = Result.Idle
    }

}