package com.example.stylish.domain.repository

import com.example.stylish.domain.model.User
import com.example.stylish.util.Result

interface ProfileAndAddressUpdateRepository {

    suspend fun getUsername(): User
    suspend fun updateUsername( newUsername: String): Result<String>

}