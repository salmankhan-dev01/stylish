package com.example.stylish.util

sealed class Result<out T> {
    data object Idle : Result<Nothing>()//kuch huwa hai nahi ab tak
    data object Loading : Result<Nothing>()//operation chal raha hai(jaise api request ja raha hai)
    data class Success<T>(val data: T) : Result<T>()//operation execute ho raha hai is success then data milaga
    data class Failure(val message: String) : Result<Nothing>()//if error mila usko print kar denga
}