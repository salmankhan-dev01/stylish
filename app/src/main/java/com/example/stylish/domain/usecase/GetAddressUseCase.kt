package com.example.stylish.domain.usecase

import com.example.stylish.domain.repository.AddressAccountRepository

class GetAddressUseCase(private val repo: AddressAccountRepository) {
    suspend operator fun invoke() = repo.getAddress()
}