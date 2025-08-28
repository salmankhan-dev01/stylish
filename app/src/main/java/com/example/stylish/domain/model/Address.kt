package com.example.stylish.domain.model

data class Address(
    val id: Int = 0,
    val pinCode: String,
    val address: String,
    val city: String,
    val state: String,
    val country: String
)