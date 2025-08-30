package com.example.stylish.domain.repository

import com.example.stylish.domain.model.Product
import com.example.stylish.util.Result

interface ProductRepository {
    suspend fun getProducts(): Result<List<Product>>

}