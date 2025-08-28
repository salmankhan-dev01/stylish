package com.example.stylish.domain.usecase

import com.example.stylish.domain.repository.AddressAccountRepository
import com.example.stylish.util.Result

class LogoutUseCase(
    private val repository: AddressAccountRepository
) {
    suspend operator fun invoke(): Result<String> {
        return repository.logout()
    }
}