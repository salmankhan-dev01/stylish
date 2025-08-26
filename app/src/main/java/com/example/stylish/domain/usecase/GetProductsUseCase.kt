package com.example.stylish.domain.usecase

import com.example.stylish.domain.model.Product
import com.example.stylish.domain.repository.ProductRepository
import com.example.stylish.util.Result

class GetProductsUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): Result<List<Product>>{
        return repository.getProducts()
    }
}