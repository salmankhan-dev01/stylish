package com.example.stylish.domain.usecase


import com.example.stylish.domain.repository.ProductRepository
import com.example.stylish.util.Result

class IsFavorite(private val repo: ProductRepository) {
    suspend operator fun invoke(productId:String):Boolean = repo.isFavorites(
        productId =productId
    )
}