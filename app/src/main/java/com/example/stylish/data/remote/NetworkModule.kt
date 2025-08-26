package com.example.stylish.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


object NetworkModule {
    private const val BASE_URL="https://dummyjson.com/"

    private val httpClient= HttpClient(Android) {
        defaultRequest {
            url(BASE_URL)
        }
        install(ContentNegotiation){
            json(Json{
                ignoreUnknownKeys=true
                isLenient=true
            })
        }
        install(Logging){
            level= LogLevel.BODY
        }
    }
    val productApiService: ProductApiService= ProductApiService(httpClient)
}