package com.example.stylish.data.repository

import com.example.stylish.domain.model.CartItem
import com.example.stylish.domain.repository.OrderProduct
import com.example.stylish.util.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class OrderProductImpl(private val auth: FirebaseAuth,private val firestore: FirebaseFirestore) : OrderProduct {
    override suspend fun addToOrder(): Result<String> {
        val uid = auth.currentUser?.uid
            ?: return Result.Failure("User not logged in")
        val cartCollection = firestore.collection("users")
            .document(uid)
            .collection("cart")

        val ordersCollection = firestore.collection("users")
            .document(uid)
            .collection("orders")
        return try {
            val querySnapshot=cartCollection.get().await()
            if(querySnapshot.isEmpty) return Result.Failure("Not data in cart")
            val batch=firestore.batch()
            for(document in querySnapshot.documents){
                val data = document.data?.toMutableMap() ?: continue

                // Store productId inside the document for reference
                data["createdAt"] = System.currentTimeMillis()

                // Create a new document with auto-generated ID to allow duplicates
                val orderDocRef = ordersCollection.document()
                batch.set(orderDocRef, data)

                batch.delete(document.reference)
            }
            batch.commit().await()
            Result.Success("The data is successfully added")
        }catch (e: Exception){
            Result.Failure(e.message?:"Some error Occured")
        }
    }
    override suspend fun getToOrder(): Result<List<CartItem>> {
        val uid = auth.currentUser?.uid
            ?: return Result.Failure("User not logged in")
        return try {
            val snapshot = firestore.collection("users")
                .document(uid)
                .collection("orders")
                .orderBy("createdAt")
                .get()
                .await()

            val cartItems = snapshot.documents.mapNotNull { it.toObject(CartItem::class.java) }
            Result.Success(cartItems)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage ?: "Error fetching cart")
        }
    }
}