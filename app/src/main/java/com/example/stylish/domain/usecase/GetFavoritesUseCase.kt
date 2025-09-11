package com.example.stylish.domain.usecase

import com.example.stylish.domain.repository.ProductRepository
import com.example.stylish.util.Result

class GetFavoritesUseCase(private val repo: ProductRepository) {
    suspend operator fun invoke(): Result<List<String>> = repo.getFavorites()
}