package com.example.stylish.domain.repository


import com.example.stylish.data.local.entity.AddressEntity
import com.example.stylish.data.local.entity.BankAccountEntity
import com.example.stylish.data.local.entity.UserEntity
import com.example.stylish.util.Result

interface AddressAccountRepository {
    suspend fun addAddress(address: AddressEntity): Result<String>
    suspend fun getAddress(): Result<AddressEntity>

    suspend fun addBankAccount(account: BankAccountEntity): Result<String>
    suspend fun getBankAccount(): Result<BankAccountEntity>

    suspend fun addUser(user : UserEntity): Result<String>
    suspend fun getUser(): Result<UserEntity>

    // Logout function
    suspend fun logout(): Result<String>

}
