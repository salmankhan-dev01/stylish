package com.example.stylish.presentation.view.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stylish.domain.model.Product
import com.example.stylish.domain.usecase.GetProductsUseCase
import com.example.stylish.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {
    private val _productsState= MutableStateFlow<Result<List<Product>>>(Result.Idle)
    val productState: StateFlow<Result<List<Product>>> =_productsState.asStateFlow()
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
}