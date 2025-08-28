package com.example.stylish.domain.usecase

import com.example.stylish.data.local.entity.BankAccountEntity
import com.example.stylish.domain.repository.AddressAccountRepository
import com.example.stylish.util.Result

class AddBankAccountUseCase(private val repo: AddressAccountRepository) {
    suspend operator fun invoke(account: BankAccountEntity) : Result<String>{
        return repo.addBankAccount(account)
    }
}