package com.example.stylish.domain.model


import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val discountPercentage: Double = 0.0,
    val rating: Double = 0.0,
    val stock: Int = 0,
    val brand: String = "",
    val category: String = "",
    val thumbnail: String = "",
    val images: List<String> = emptyList()
)