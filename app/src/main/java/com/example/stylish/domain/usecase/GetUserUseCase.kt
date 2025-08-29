package com.example.stylish.domain.usecase

import com.example.stylish.domain.repository.AddressAccountRepository

class GetUserUseCase(private val repo: AddressAccountRepository){
    suspend operator fun invoke()=repo.getUser()
}