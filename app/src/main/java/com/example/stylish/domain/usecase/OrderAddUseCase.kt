package com.example.stylish.domain.usecase

import com.example.stylish.domain.repository.OrderProduct
import com.example.stylish.util.Result

class OrderAddUseCase(private val repo: OrderProduct) {

    suspend operator fun invoke(): Result<String>{
        return repo.addToOrder()
    }

}