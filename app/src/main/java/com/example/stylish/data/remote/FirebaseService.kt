package com.example.stylish.data.remote


import android.util.Log
import com.example.stylish.data.local.entity.AddressEntity
import com.example.stylish.data.local.entity.BankAccountEntity
import com.example.stylish.data.local.entity.UserEntity
import com.example.stylish.data.remote.dto.AddressDto
import com.example.stylish.data.remote.dto.BankAccountDto
import com.example.stylish.data.remote.dto.UserDto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseService(
    val auth: FirebaseAuth,
    val db: FirebaseFirestore
) {

    private val uid get() = auth.currentUser?.uid ?: throw Exception("User not logged in")

    // Address
    suspend fun saveAddress(address: AddressEntity) {
        val dto = AddressDto(
            pinCode = address.pinCode,
            address = address.address,
            city = address.city,
            state = address.state,
            country = address.country
        )
        db.collection("users").document(uid)
            .collection("address").document("primary")
            .set(dto).await()
    }

    suspend fun getAddress(): AddressEntity? {
        return try {
            val doc = db.collection("users").document(uid)
                .collection("address").document("primary")
                .get().await()
            val dto = doc.toObject(AddressDto::class.java) ?: return null
            AddressEntity(
                id = 0, // Room ke liye auto-generate
                pinCode = dto.pinCode,
                address = dto.address,
                city = dto.city,
                state = dto.state,
                country = dto.country
            )
        } catch (e: Exception) {
            null
        }
    }

    // BankAccount
    suspend fun saveBankAccount(account: BankAccountEntity) {
        val dto= BankAccountDto(
            accountHolder = account.accountHolder,
            accountNumber = account.accountNumber,
            ifscCode = account.ifscCode
        )
        db.collection("users").document(uid)
            .collection("bank_account").document("primary")
            .set(dto).await()
    }

    suspend fun getBankAccount(): BankAccountEntity? {
        return try {
            val doc = db.collection("users").document(uid)
                .collection("bank_account").document("primary")
                .get().await()
            val dto = doc.toObject(BankAccountDto::class.java) ?: return null

            BankAccountEntity(
                id = 0,
                accountHolder = dto.accountHolder,
                accountNumber = dto.accountNumber,
                ifscCode = dto.ifscCode
            )
        }catch (e: Exception) {

            return null
        }
    }

    suspend fun saveUser(user: UserEntity){
        val dto= UserDto(
            name =user.name,
            email = user.email
        )
        db.collection("users").document(uid)
            .collection("user_details").document("primary")
            .set(dto).await()
    }

    suspend fun getUser(): UserEntity?{
        return try {
            val doc= db.collection("users").document(uid)
                .collection("user_details").document("primary")
                .get().await()
            val dto=doc.toObject(UserDto::class.java)?: return  null
            UserEntity(
                id = 0,
                name = dto.name,
                email = dto.email,

            )
        }catch (e: Exception){
            return null
        }
    }

    fun logout() {
        auth.signOut() // Firebase logout
    }
}
