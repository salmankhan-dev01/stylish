package com.example.stylish.domain.usecase

import com.example.stylish.domain.repository.ProductRepository
import com.example.stylish.util.Result

class AddToCartUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke(productId: String, quantity: Int): Result<String>{
        return repository.addToCart(productId,quantity)
    }
}