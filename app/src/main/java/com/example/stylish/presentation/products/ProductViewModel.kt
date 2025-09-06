package com.example.stylish.presentation.products

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stylish.domain.model.CartItem
import com.example.stylish.domain.model.Product
import com.example.stylish.domain.usecase.AddToCartUseCase
import com.example.stylish.domain.usecase.GetCartItemsUseCase
import com.example.stylish.domain.usecase.GetProductsUseCase
import com.example.stylish.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val getToCartUseCase: GetCartItemsUseCase
) : ViewModel() {
    private val _productsState= MutableStateFlow<Result<List<Product>>>(Result.Idle)
    val productState: StateFlow<Result<List<Product>>> =_productsState.asStateFlow()

    private val _getCart=MutableStateFlow<Result<List<CartItem>>>(Result.Idle)
    val getCart: StateFlow<Result<List<CartItem>>> =_getCart

    private val _addCart=MutableStateFlow<Result<String>>(Result.Idle)
    val addCart: StateFlow<Result<String>> =_addCart

    init {
        loadProducts()
    }
    fun loadProducts(){
        Log.d("ProductViewModel", "Loading products")
        _productsState.value= Result.Loading
        viewModelScope.launch {
            try {
                val result=getProductsUseCase()
                Log.d("ProductViewModel", "Products loaded: $result")
                _productsState.value=result
            }catch (e: Exception){
                _productsState.value= Result.Failure(e.message?:"Unknown error")
            }
        }
    }
    fun retryLoading(){
        loadProducts()
    }

    fun getToCart(){
        _getCart.value= Result.Loading
        viewModelScope.launch {
            try {
                val result=getToCartUseCase()
                _getCart.value=result
            }catch (e: Exception){
                _getCart.value= Result.Failure(e.message?:"Unknown error")
            }
        }
    }

    fun addToCart(productId: String,quantity:Int){
        _addCart.value= Result.Loading
        viewModelScope.launch {
            try {
                val result=addToCartUseCase(productId,quantity)
                _addCart.value=result
                getToCart()
            }catch (e: Exception){
                _addCart.value= Result.Failure(e.message?:"Unknown error")
            }
        }
    }


}