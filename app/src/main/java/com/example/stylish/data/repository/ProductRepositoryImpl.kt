package com.example.stylish.data.repository

import android.util.Log
import com.example.stylish.data.remote.ProductApiService
import com.example.stylish.domain.model.CartItem
import com.example.stylish.domain.model.FavoriteItem
import com.example.stylish.domain.model.Product
import com.example.stylish.domain.repository.ProductRepository
import com.example.stylish.util.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.protobuf.LazyStringArrayList.emptyList
import kotlinx.coroutines.tasks.await

class ProductRepositoryImpl(private val apiService: ProductApiService,private val firestore: FirebaseFirestore,private val auth: FirebaseAuth): ProductRepository {

    override suspend fun getProducts(): Result<List<Product>> {
        return  try {
            Log.d("ProductRepository", "Fetching products from API")
            val response=apiService.getProducts()
            Log.d("ProductRepository", "Received ${response.products.size} products")
            Result.Success(response.products)
        }catch (e: Exception){
            Log.e("ProductRepository", "Error fetching products: ${e.message}", e)
            Result.Failure(e.localizedMessage?:"Unknown error occurred")
        }
    }

    override suspend fun addToCart(productId:String,quantity: Int): Result<String> {
        val uid = auth.currentUser?.uid
            ?: return Result.Failure("User not logged in")
        val cartCollection = firestore.collection("users")
            .document(uid)
            .collection("cart")

        val productDocRef = cartCollection.document(productId)
        return try {
            // Firestore se existing product check karo
            val snapshot = productDocRef.get().await()
            if (snapshot.exists()) {
                // Product already in cart → quantity update
                val currentQuantity = snapshot.getLong("quantity") ?: 0
                productDocRef.update("quantity", currentQuantity + quantity).await()
            } else {
                // Product not in cart → naya document create karo
                val cartItem = CartItem(productId = productId, quantity = quantity)
                productDocRef.set(cartItem).await()
            }
            Result.Success("Added to cart")
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage ?: "Error adding to cart")
        }

    }

    override suspend fun getToCart(): Result<List<CartItem>> {
        val uid = auth.currentUser?.uid
            ?: return Result.Failure("User not logged in")
        return try {
            val snapshot = firestore.collection("users")
                .document(uid)
                .collection("cart")
                .get()
                .await()

            val cartItems = snapshot.documents.mapNotNull { it.toObject(CartItem::class.java) }
            Result.Success(cartItems)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage ?: "Error fetching cart")
        }
    }

    override suspend fun toggleFavorite(productId: String): Boolean {
        val uid = auth.currentUser?.uid
            ?: return false

        val favoritesCollection = firestore.collection("users")
            .document(uid)
            .collection("favorites")

        val productDocRef = favoritesCollection.document(productId)

        return try {
            val snapshot = productDocRef.get().await()
            if (snapshot.exists()) {
                // Already favorite → remove it
                productDocRef.delete().await()
                false
            } else {
                // Not favorite → add it
                val favItem = FavoriteItem(productId)
                productDocRef.set(favItem).await()
                true
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getFavorites():Result<List<String>>{
        val uid = auth.currentUser?.uid ?:return Result.Failure("User not logged in")
        return try {
            val snapshot = firestore.collection("users")
                .document(uid)
                .collection("favorites")
                .get()
                .await()

            val favorites = snapshot.documents.mapNotNull { it.id }
            Result.Success(favorites)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage ?: "Error fetching favorites")
        }

        }

    override suspend fun isFavorites(productId: String): Boolean {
        val uid = auth.currentUser?.uid ?:return false
        return try {
            val snapshot = firestore.collection("users")
                .document(uid)
                .collection("favorites")
                .document(productId)
                .get()
                .await()
            snapshot.exists()
        } catch (e: Exception) {
            false
        }
    }


}