package com.example.stylish.data.remote

import com.example.stylish.domain.model.ProductResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ProductApiService(private val client: HttpClient) {
    suspend fun getProducts(limit:Int=0): ProductResponse {
        return  client.get("products"){
            parameter("limit",limit)
        }.body()

    }
}
