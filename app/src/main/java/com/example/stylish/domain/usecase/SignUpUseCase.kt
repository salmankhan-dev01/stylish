package com.example.stylish.domain.usecase

import com.example.stylish.domain.repository.AuthRepository
import com.example.stylish.util.Result

class SignUpUseCase(private  val repository: AuthRepository){
    suspend operator  fun invoke(email:String,password:String): Result<String> {
        return  repository.signup(email,password)
    }
}