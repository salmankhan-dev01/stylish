package com.example.stylish.domain.usecase

import com.example.stylish.data.local.entity.UserEntity
import com.example.stylish.domain.repository.AddressAccountRepository
import com.example.stylish.util.Result

class AddUserUseCase(private val repo: AddressAccountRepository) {
    suspend operator fun invoke(user: UserEntity) : Result<String>{
        return repo.addUser(user)
    }
}