package com.example.stylish.data.repository


import android.util.Log
import com.example.stylish.domain.model.User
import com.example.stylish.domain.repository.AuthRepository
import com.example.stylish.util.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await


class AuthRepositoryImpl(private val firebaseAuth: FirebaseAuth,private  val firestore: FirebaseFirestore): AuthRepository {
    override suspend fun login(email: String, password: String): Result<String> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            delay(1000)
            val uid = firebaseAuth.currentUser?.uid ?: throw Exception("User not logged in")
            Log.d("uidname",uid)

            Result.Success("Login Successful")
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage ?: "Unknown error during login")
        }
    }



    override suspend fun signup(email: String, password: String): Result<String> {
        return try {
            val authResult=firebaseAuth.createUserWithEmailAndPassword(email, password).await()

            val authUser=authResult.user!!

            // Authentication me displayName = email (default username)
            val profileUpdates= UserProfileChangeRequest.Builder()
                .setDisplayName(email.substringBefore("@"))
                .build()
            authUser.updateProfile(profileUpdates).await()
            //Firestore me User object save
            val user = User(
                username = email.substringBefore("@"),  // default username = email
                email = email,
                password=password
            )
            firestore.collection("users")
                .document(authUser.uid)
                .set(user)
                .await()
                    delay(1000)
                    Result.Success("Signup Successful")
                } catch (e: Exception) {
                    Result.Failure(e.localizedMessage ?: "Unknown error during signup")
                }
    }
}