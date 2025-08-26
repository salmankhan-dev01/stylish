package com.example.stylish.presentation.viewmodel.authViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stylish.domain.usecase.LoginUseCase
import com.example.stylish.domain.usecase.SignUpUseCase
import kotlin.jvm.java

class AuthViewModelFactory(
    private val loginUseCase: LoginUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(loginUseCase, signUpUseCase) as T
        }
        throw kotlin.IllegalArgumentException("Unknown ViewModel class")
    }
}



