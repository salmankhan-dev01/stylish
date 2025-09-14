package com.example.stylish.domain.usecase

import com.example.stylish.domain.model.CartItem
import com.example.stylish.domain.repository.OrderProduct
import com.example.stylish.util.Result

class OrderGetUseCase (private val repo: OrderProduct){

    suspend operator fun invoke(): Result<List<CartItem>>{
        return repo.getToOrder()
    }

}