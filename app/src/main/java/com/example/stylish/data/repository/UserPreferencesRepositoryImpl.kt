package com.example.stylish.data.repository

import com.example.stylish.data.local.UserPreferencesDataStore
import com.example.stylish.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow

class UserPreferencesRepositoryImpl(
    private val userPreferencesDataStore: UserPreferencesDataStore
) : UserPreferencesRepository{
    override val isFirstTimeLogin: Flow<Boolean> =userPreferencesDataStore.isFirstTimeLogin
    override val isLoggedIn: Flow<Boolean> =userPreferencesDataStore.isLoggedIn

    override suspend fun setFirstTimeLogin(isFirstTime: Boolean) {
        userPreferencesDataStore.setFirstTimeLogin(isFirstTime)
    }

    override suspend fun setLoggedIn(isLoggedIn: Boolean) {
        userPreferencesDataStore.setLoggedIn(isLoggedIn)
    }
}