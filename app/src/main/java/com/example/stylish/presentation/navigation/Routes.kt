package com.example.stylish.presentation.navigation

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

}
