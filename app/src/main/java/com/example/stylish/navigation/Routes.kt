package com.example.stylish.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes(){
    @Serializable
    data object HomeScreen: Routes()
    @Serializable
    data object SplashScreen: Routes()
    @Serializable
    data object SkipScreen1: Routes()
    @Serializable
    data object SkipScreen2: Routes()
    @Serializable
    data object SkipScreen3: Routes()
    @Serializable
    data object LoginScreen: Routes()
    @Serializable
    data object SignupScreen: Routes()
    @Serializable
    data object ForgotScreen: Routes()
    @Serializable
    data object ProductScreen: Routes()
    @Serializable
    data object ViewAll:Routes()

    @Serializable
    data class ProductDetailScreen(val productId:Int): Routes()

    @Serializable
    data object UserProfile: Routes()

    @Serializable
    data object PlaceOrderScreen: Routes()

    @Serializable
    data class PaymentScreen(val totalPrice: Double): Routes()

    @Serializable
    data class CategoryProduct(val productCategory: String): Routes()

    @Serializable
    data object FavoriteProduct: Routes()
    @Serializable
    data object OrderScreen: Routes()

}
