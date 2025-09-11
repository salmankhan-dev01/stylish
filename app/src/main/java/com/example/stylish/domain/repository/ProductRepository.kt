package com.example.stylish.domain.repository

import com.example.stylish.domain.model.CartItem
import com.example.stylish.domain.model.Product
import com.example.stylish.util.Result

interface ProductRepository {
    suspend fun getProducts(): Result<List<Product>>

    suspend fun addToCart(productId:String,quantity: Int): Result<String>

    suspend fun getToCart(): Result<List<CartItem>>

    suspend fun toggleFavorite(productId: String): Boolean
    suspend fun getFavorites(): Result<List<String>>

    suspend fun isFavorites(productId: String): Boolean
}