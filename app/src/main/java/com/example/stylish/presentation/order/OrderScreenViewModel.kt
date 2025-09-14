package com.example.stylish.presentation.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stylish.domain.model.CartItem
import com.example.stylish.domain.usecase.OrderAddUseCase
import com.example.stylish.domain.usecase.OrderGetUseCase
import com.example.stylish.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderScreenViewModel(
    private val orderToAddUseCase: OrderAddUseCase,
    private val orderToGetUseCase: OrderGetUseCase
): ViewModel() {
    private val _addOrderState= MutableStateFlow<Result<String>>(Result.Idle)
    val addOrderState: StateFlow<Result<String>> =_addOrderState

    private val _getOrderState= MutableStateFlow<Result<List<CartItem>>>(Result.Idle)
    val getOrderState: StateFlow<Result<List<CartItem>>> =_getOrderState


    fun addOrderProduct(){
        viewModelScope.launch {
            _addOrderState.value= Result.Loading
            val result=orderToAddUseCase()
            _addOrderState.value=result
        }

    }
    fun getOrderProduct(){
        viewModelScope.launch {
            _getOrderState.value= Result.Loading
            val result=orderToGetUseCase()
            _getOrderState.value=result
        }

    }
    fun resetOrderState() {
        _addOrderState.value = Result.Idle
    }

}