package com.example.stylish.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.stylish.data.local.AppDatabaseProvider
import com.example.stylish.data.local.UserPreferencesDataStore
import com.example.stylish.data.remote.FirebaseService
import com.example.stylish.data.remote.NetworkModule
import com.example.stylish.data.repository.AddressAccountRepositoryImpl
import com.example.stylish.data.repository.AuthRepositoryImpl
import com.example.stylish.data.repository.ProductRepositoryImpl
import com.example.stylish.data.repository.UserPreferencesRepositoryImpl
import com.example.stylish.domain.usecase.AddAddressUseCase
import com.example.stylish.domain.usecase.AddBankAccountUseCase
import com.example.stylish.domain.usecase.AddUserUseCase
import com.example.stylish.domain.usecase.GetAddressUseCase
import com.example.stylish.domain.usecase.GetBankAccountUseCase
import com.example.stylish.domain.usecase.GetProductsUseCase
import com.example.stylish.domain.usecase.GetUserPreferencesUseCase
import com.example.stylish.domain.usecase.GetUserUseCase
import com.example.stylish.domain.usecase.LoginUseCase
import com.example.stylish.domain.usecase.LogoutUseCase
import com.example.stylish.domain.usecase.SetUserPreferencesUseCase
import com.example.stylish.domain.usecase.SignUpUseCase
import com.example.stylish.presentation.userPreference.UserPreferencesViewModel
import com.example.stylish.presentation.auth.ForgotScreen
import com.example.stylish.presentation.auth.LoginScreen
import com.example.stylish.presentation.auth.SignupScreen
import com.example.stylish.presentation.onboarding.SkipScreen1
import com.example.stylish.presentation.onboarding.SkipScreen2
import com.example.stylish.presentation.onboarding.SkipScreen3
import com.example.stylish.presentation.products.HomeScreen
import com.example.stylish.presentation.products.ProductDetailScreen
import com.example.stylish.presentation.products.ProductScreen
import com.example.stylish.presentation.products.ProductViewModel
import com.example.stylish.presentation.products.ViewAll
import com.example.stylish.presentation.splash.SplashScreen
import com.example.stylish.presentation.auth.AuthViewModel
import com.example.stylish.presentation.auth.AuthViewModelFactory
import com.example.stylish.presentation.products.PlaceOrderScreen
import com.example.stylish.presentation.profile.AddressAccountViewModel
import com.example.stylish.presentation.profile.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun Navigation(){
    //AK HI NAV CONTROLLER MAX 2
    val navController= rememberNavController()
    val context= LocalContext.current



    //for fetch product form api
    val productPepo= remember{ ProductRepositoryImpl(NetworkModule.productApiService) }
    val getProductsUseCase=remember { GetProductsUseCase(productPepo) }
    val productViewModel=remember { ProductViewModel(getProductsUseCase) }

    // for user preferences
    val userPreferencesDataStore=remember { UserPreferencesDataStore(context) }
    val userPreferencesRepo=remember { UserPreferencesRepositoryImpl(userPreferencesDataStore) }
    val getUserPreferencesUseCase=remember { GetUserPreferencesUseCase(userPreferencesRepo) }
    val setUserPreferencesUseCase=remember { SetUserPreferencesUseCase(userPreferencesRepo) }
    val userPreferenceViewModel=remember { UserPreferencesViewModel(getUserPreferencesUseCase,setUserPreferencesUseCase) }
    val userPreferenceState by userPreferenceViewModel.state.collectAsState()


//    // Username and Address adding and updating
//    val profileAndAddressUpdateRepositoryImpl=remember { ProfileAndAddressUpdateRepositoryImpl(
//        FirebaseAuth.getInstance(),FirebaseFirestore.getInstance()) }
//    val getUsernameUsecase=remember { GetUsernameUsecase(profileAndAddressUpdateRepositoryImpl) }
//    val profileAndAddressUsecase=remember { ProfileAndAddressUsecase(profileAndAddressUpdateRepositoryImpl) }
//    val profileViewModel=remember { ProfileViewModel(getUsernameUsecase,profileAndAddressUsecase) }

// ------------------------- profile me user address and account room+firebase ----------------------------
    // 1️⃣ Room / Dao instances
    val db = remember { AppDatabaseProvider.getDatabase(context) }
    val addressDao = remember { db.addressDao() }
    val bankAccountDao = remember { db.bankAccountDao() }
    val userDao=remember { db.userDao() }

    // 2️⃣ Firebase Service
    val firebaseService = remember { FirebaseService(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance()) }

    // 3️⃣ Repository
    val repository = remember { AddressAccountRepositoryImpl(userDao,addressDao, bankAccountDao, firebaseService) }

    // 4️⃣ UseCases
    val addAddressUseCase = remember { AddAddressUseCase(repository) }
    val getAddressUseCase = remember { GetAddressUseCase(repository) }
    val addBankAccountUseCase = remember { AddBankAccountUseCase(repository) }
    val getBankAccountUseCase = remember { GetBankAccountUseCase(repository) }
    val addUserUseCase=remember { AddUserUseCase(repository) }
    val getUserUseCase=remember { GetUserUseCase(repository) }
    val logoutUseCase = remember { LogoutUseCase(repository) }
    // 5️⃣ ViewModel
    val viewModel1 = remember { AddressAccountViewModel(addAddressUseCase, getAddressUseCase, addBankAccountUseCase, getBankAccountUseCase,addUserUseCase,getUserUseCase,logoutUseCase) }



    //For login and signup
    val authRepository = AuthRepositoryImpl(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance(),firebaseService,userDao)
    val loginUseCase = LoginUseCase(authRepository)
    val signUpUseCase = SignUpUseCase(authRepository)
    val factory = AuthViewModelFactory(loginUseCase, signUpUseCase)
    // Get ViewModel with factory
    val viewModel: AuthViewModel = viewModel(factory = factory)

    NavHost(navController=navController, startDestination = Routes.ProductScreen){
        //NAV GRAPH
        composable<Routes.SplashScreen> {
            SplashScreen()
        }
        composable<Routes.SkipScreen1> {
            SkipScreen1(navController)
        }
        composable<Routes.SkipScreen2> {
            SkipScreen2(navController)
        }
        composable<Routes.SkipScreen3> {
            SkipScreen3(navController)
        }
        composable<Routes.LoginScreen> {
            LoginScreen(navController,viewModel,userPreferenceViewModel)
        }
        composable<Routes.SignupScreen> {
            SignupScreen(navController,viewModel)
        }
        composable<Routes.ForgotScreen> {
            ForgotScreen(navController)
        }
        composable<Routes.HomeScreen> {
            HomeScreen(navController)
        }
        composable<Routes.ProductScreen> {
            ProductScreen(navController,productViewModel)
        }
        composable<Routes.ViewAll> {
            ViewAll(navController,productViewModel)
        }
        composable<Routes.ProductDetailScreen> {backStackEntry->
            val args=backStackEntry.toRoute<Routes.ProductDetailScreen>()
            ProductDetailScreen(navController,productViewModel,args.productId)
        }
        composable<Routes.UserProfile> {
            UserProfile(viewModel1,navController)
        }
        composable <Routes.PlaceOrderScreen>{
            PlaceOrderScreen()
        }
    }
//    LaunchedEffect(userPreferenceState.isLoading,userPreferenceState.isLoggedIn,userPreferenceState.isFirstTimeLogin) {
//        if(!userPreferenceState.isLoading){
//            val destination=when{
//                userPreferenceState.isLoggedIn-> Routes.ProductScreen
//                userPreferenceState.isFirstTimeLogin-> Routes.SkipScreen1
//                else-> Routes.LoginScreen
//            }
//            navController.navigate(destination){
//                popUpTo(Routes.SplashScreen) {
//                    inclusive=true
//                }
//            }
//        }
//    }

}