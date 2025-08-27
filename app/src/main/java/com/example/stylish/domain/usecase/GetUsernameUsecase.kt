package com.example.stylish.domain.usecase

import com.example.stylish.domain.model.User
import com.example.stylish.domain.repository.ProfileAndAddressUpdateRepository

class GetUsernameUsecase(private val repository:  ProfileAndAddressUpdateRepository) {
    suspend operator fun invoke(): User {
        return repository.getUsername()
    }
}