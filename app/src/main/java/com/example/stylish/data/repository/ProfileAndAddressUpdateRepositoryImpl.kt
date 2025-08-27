package com.example.stylish.data.repository

import android.util.Log
import com.example.stylish.domain.model.User
import com.example.stylish.domain.repository.ProfileAndAddressUpdateRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.example.stylish.util.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class ProfileAndAddressUpdateRepositoryImpl(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) : ProfileAndAddressUpdateRepository{
    override suspend fun getUsername(): User {
        val uid = auth.currentUser?.uid ?: throw Exception("User not logged in")
        Log.d("uidname",uid)
        val doc = fireStore.collection("users").document(uid).get().await()
        Log.d("username",doc.getString("username")?:"")
        val username=doc.getString("username") ?: "no name"
        val email=doc.getString("email") ?: "no name"
        return User(username,email,"********")
    }

    override suspend fun updateUsername(newUsername: String, ): Result<String> {
        return try {
            val currentUser = auth.currentUser?: throw Exception("User not logged in")
            val uid=currentUser.uid
            // 1. Update Firebase Auth displayName
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(newUsername)
                .build()
            currentUser.updateProfile(profileUpdates).await()

            // 2. Update Firestore username
            Log.d("uidname",uid)
            fireStore.collection("users").document(uid)
                .update("username", newUsername).await()
            Result.Success("Username update suceess")
        } catch (e: Exception) {
            Result.Failure(e.message?:"Unknown error")
        }
    }

}