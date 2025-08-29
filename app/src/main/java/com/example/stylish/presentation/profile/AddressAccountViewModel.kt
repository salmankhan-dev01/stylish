package com.example.stylish.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stylish.data.local.entity.AddressEntity
import com.example.stylish.data.local.entity.BankAccountEntity
import com.example.stylish.data.local.entity.UserEntity
import com.example.stylish.domain.usecase.*
import com.example.stylish.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddressAccountViewModel(
    private val addAddressUseCase: AddAddressUseCase,
    private val getAddressUseCase: GetAddressUseCase,
    private val addBankAccountUseCase: AddBankAccountUseCase,
    private val getBankAccountUseCase: GetBankAccountUseCase,
    private val addUserUseCase: AddUserUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    // ---------------- Address ----------------
    private val _saveAddressResult = MutableStateFlow<Result<String>>(Result.Idle)
    val saveAddressResult: StateFlow<Result<String>> = _saveAddressResult

    private val _addressResult = MutableStateFlow<Result<AddressEntity>>(Result.Idle)
    val addressResult: StateFlow<Result<AddressEntity>> = _addressResult

    // ---------------- Bank Account ----------------
    private val _saveBankResult = MutableStateFlow<Result<String>>(Result.Idle)
    val saveBankResult: StateFlow<Result<String>> = _saveBankResult

    private val _bankResult = MutableStateFlow<Result<BankAccountEntity>>(Result.Idle)
    val bankResult: StateFlow<Result<BankAccountEntity>> = _bankResult

    private val _saveUserResult = MutableStateFlow<Result<String>>(Result.Idle)
    val saveUserResult: StateFlow<Result<String>> = _saveUserResult

    private val _userResult = MutableStateFlow<Result<UserEntity>>(Result.Idle)
    val userResult: StateFlow<Result<UserEntity>> = _userResult

    private val _logoutUser = MutableStateFlow<Result<String>>(Result.Idle)
    val logout: StateFlow<Result<String>> = _logoutUser


    // ---------------- Save Operations ----------------
    fun saveAddress(address: AddressEntity) = viewModelScope.launch {
        // Validation: check if any field is empty
        if (address.pinCode.isBlank() || address.address.isBlank() ||
            address.city.isBlank() || address.state.isBlank() || address.country.isBlank()
        ) {
            _saveAddressResult.value = Result.Failure("Please fill all address fields")
            return@launch
        }
        _saveAddressResult.value = Result.Loading
        _saveAddressResult.value = addAddressUseCase(address)
    }

    fun saveBankAccount(account: BankAccountEntity) = viewModelScope.launch {

        // Validation: check if any field is empty
        if (account.accountNumber.isBlank() || account.accountHolder.isBlank() || account.ifscCode.isBlank()) {
            _saveBankResult.value = Result.Failure("Please fill all bank fields")
            return@launch
        }

        _saveBankResult.value = Result.Loading
        _saveBankResult.value = addBankAccountUseCase(account)
    }

    fun saveUser(user: UserEntity) = viewModelScope.launch {

        // Validation: check if any field is empty
        if (user.name.isBlank()) {
            _saveBankResult.value = Result.Failure("Please fill all bank fields")
            return@launch
        }

        _saveUserResult.value = Result.Loading
        _saveUserResult.value = addUserUseCase(user)
    }

    // ---------------- Fetch Operations ----------------
    fun fetchAddress() = viewModelScope.launch {
        _addressResult.value = Result.Loading
        _addressResult.value = getAddressUseCase()
    }

    fun fetchBankAccount() = viewModelScope.launch {
        _bankResult.value = Result.Loading
        _bankResult.value = getBankAccountUseCase()
    }

    fun fetchUser() = viewModelScope.launch {
        _userResult.value = Result.Loading
        _userResult.value =getUserUseCase()
    }
    fun logoutAccout(){
        viewModelScope.launch {
            _logoutUser.value= Result.Loading
            _logoutUser.value= logoutUseCase()
        }
    }
}
