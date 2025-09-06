package com.example.stylish.domain.usecase

import com.example.stylish.domain.model.CartItem
import com.example.stylish.domain.repository.ProductRepository
import com.example.stylish.util.Result

class GetCartItemsUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke(): Result<List<CartItem>>{
        return  repository.getToCart()
    }
}