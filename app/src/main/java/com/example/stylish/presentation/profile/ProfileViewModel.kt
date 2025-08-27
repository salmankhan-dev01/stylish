package com.example.stylish.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stylish.domain.model.User
import com.example.stylish.domain.usecase.GetUsernameUsecase
import com.example.stylish.domain.usecase.ProfileAndAddressUsecase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.stylish.util.Result

class ProfileViewModel(
    private val getUsernameUsecase: GetUsernameUsecase,
        private val profileAndAddressUsecase: ProfileAndAddressUsecase
    ) : ViewModel() {

    private val _updateResult = MutableStateFlow<Result<String>>(Result.Idle)
    val updateResult: StateFlow<Result<String>> = _updateResult


    // Username state
    val user = MutableStateFlow(User("","",""))
     fun fetchUsername() {
        viewModelScope.launch {
            try {
                user.value = getUsernameUsecase() // fetch from Firestore
            } catch (e: Exception) {
                user.value = User("","","")

            }        }
    }

    fun updateUsername(newUsername: String) {
        if(newUsername.isEmpty()){
            _updateResult.value= Result.Failure("Username can't empty")
            return
        }
        _updateResult.value= Result.Loading
        viewModelScope.launch {
            try {
                val result = profileAndAddressUsecase(newUsername)
                _updateResult.value =result
                user.value = user.value.copy(username = newUsername)
            }catch (e: Exception){
                _updateResult.value= Result.Failure(e.message?:"Username update failed")

            }
        }
        }
    }

