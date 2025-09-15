package com.example.stylish.data.repository


import android.util.Log
import com.example.stylish.data.local.dao.UserDao
import com.example.stylish.data.local.dto.AddressDao
import com.example.stylish.data.local.dto.BankAccountDao
import com.example.stylish.data.local.entity.UserEntity
import com.example.stylish.data.remote.FirebaseService
import com.example.stylish.domain.model.User
import com.example.stylish.domain.repository.AuthRepository
import com.example.stylish.util.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private  val firebaseService: FirebaseService,
    private val userDao: UserDao,
    private val addressDao: AddressDao,
    private val bankAccountDao: BankAccountDao,
): AuthRepository {
    override suspend fun login(email: String, password: String): Result<String> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            //delay(1000)
            userDao.clear()
            addressDao.clear()
            bankAccountDao.clear()
            Result.Success("Login Successful")
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage ?: "Unknown error during login")
        }
    }



    override suspend fun signup(email: String, password: String): Result<String> {
        return try {
            userDao.clear()
            addressDao.clear()
            bankAccountDao.clear()
            val authResult=firebaseAuth.createUserWithEmailAndPassword(email, password).await()

            val authUser=authResult.user!!

            // Authentication me displayName = email (default username)
            val profileUpdates= UserProfileChangeRequest.Builder()
                .setDisplayName(email.substringBefore("@"))
                .build()
            authUser.updateProfile(profileUpdates).await()
//            //Firestore me User object save
//            val user = User(
//                username = email.substringBefore("@"),  // default username = email
//                email = email,
//                password=password
//            )
            val user= UserEntity(
                name = email.substringBefore("@"),
                email = email
            )
            firebaseService.saveUser(user)
            userDao.insert(user)
            //delay(1000)
            Result.Success("Signup Successful")
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage ?: "Unknown error during signup")
        }
    }
}