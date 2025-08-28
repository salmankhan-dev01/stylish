package com.example.stylish.domain.model

data class BankAccount(
    val id: Int = 0,
    val accountHolder: String,
    val accountNumber: String,
    val ifscCode: String
)