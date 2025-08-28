package com.example.stylish.domain.usecase

import com.example.stylish.data.local.entity.AddressEntity
import com.example.stylish.domain.repository.AddressAccountRepository
import com.example.stylish.util.Result

class AddAddressUseCase(private val repo: AddressAccountRepository) {
    suspend operator fun invoke(address: AddressEntity): Result<String> {
        return repo.addAddress(address)
    }
}