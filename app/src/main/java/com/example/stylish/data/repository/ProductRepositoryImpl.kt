package com.example.stylish.data.repository

import android.util.Log
import com.example.stylish.data.remote.ProductApiService
import com.example.stylish.domain.model.Product
import com.example.stylish.domain.repository.ProductRepository
import com.example.stylish.util.Result

class ProductRepositoryImpl(private val apiService: ProductApiService): ProductRepository {
    override suspend fun getProducts(): Result<List<Product>> {
        return  try {
            Log.d("ProductRepository", "Fetching products from API")
            val response=apiService.getProducts()
            Log.d("ProductRepository", "Received ${response.products.size} products")
            Result.Success(response.products)
        }catch (e: Exception){
            Log.e("ProductRepository", "Error fetching products: ${e.message}", e)
            Result.Failure(e.localizedMessage?:"Unknown error occurred")
        }
    }
}