package com.example.stylish.presentation.userPreference

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stylish.domain.model.UserPreferencesState
import com.example.stylish.domain.usecase.GetUserPreferencesUseCase
import com.example.stylish.domain.usecase.SetUserPreferencesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class UserPreferencesViewModel(
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase,
    private val setUserPreferencesUseCase: SetUserPreferencesUseCase
) : ViewModel(){
    private val _state= MutableStateFlow(UserPreferencesState())
    val state: StateFlow<UserPreferencesState> =_state.asStateFlow()
    init {
        observeUserPreferences()
    }

    private fun observeUserPreferences() {
        viewModelScope.launch {
            combine(getUserPreferencesUseCase.isFirstTimeLogin(),
                getUserPreferencesUseCase.isLoggedIn()){isFirstTime,isLoggedIn->
                UserPreferencesState(
                    isFirstTimeLogin = isFirstTime,
                    isLoggedIn=isLoggedIn,
                    isLoading = false
                )
            }.collect { newState->
                _state.value=newState
            }
        }
    }

    fun setFirstTimeLogin(isFirstTime: Boolean) {
        viewModelScope.launch {
            setUserPreferencesUseCase.setFirstTimeLogin(isFirstTime)
        }
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        viewModelScope.launch {
            setUserPreferencesUseCase.setLoggedIn(isLoggedIn)
        }
    }
}