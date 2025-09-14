package com.example.stylish.domain.repository

import com.example.stylish.domain.model.CartItem
import com.example.stylish.util.Result

interface OrderProduct {
    suspend fun addToOrder(): Result<String>
    suspend fun getToOrder(): Result<List<CartItem>>
}