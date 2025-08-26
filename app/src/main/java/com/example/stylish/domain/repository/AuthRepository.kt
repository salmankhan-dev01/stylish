package com.example.stylish.domain.repository

import com.example.stylish.util.Result


interface AuthRepository{
    suspend fun  login(email:String,password:String): Result<String>
    suspend fun signup(email: String,password: String): Result<String>
}