package com.example.stylish.data.repository


import android.util.Log
import com.example.stylish.domain.repository.AuthRepository
import com.example.stylish.util.Result
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await


class AuthRepositoryImpl(private val firebaseAuth: FirebaseAuth): AuthRepository {
    override suspend fun login(email: String, password: String): Result<String> {
        return try {
            val data=firebaseAuth.signInWithEmailAndPassword(email, password).await()
            delay(1000)
            Result.Success("Login Successful")
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage ?: "Unknown error during login")
        }
    }
    override suspend fun signup(email: String, password: String): Result<String> {
return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            delay(1000)
            Result.Success("Signup Successful")
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage ?: "Unknown error during signup")
        }
    }
}