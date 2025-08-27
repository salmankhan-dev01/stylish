package com.example.stylish.domain.usecase

import com.example.stylish.domain.repository.ProfileAndAddressUpdateRepository
import com.example.stylish.util.Result

class ProfileAndAddressUsecase(private val repository:  ProfileAndAddressUpdateRepository) {

    suspend operator fun invoke( newUsername: String): Result<String> {
        return repository.updateUsername( newUsername)
    }
}