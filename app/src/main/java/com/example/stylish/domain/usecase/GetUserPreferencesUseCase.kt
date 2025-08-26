package com.example.stylish.domain.usecase

import com.example.stylish.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow

class GetUserPreferencesUseCase(
   private val userPreferencesRepository: UserPreferencesRepository
) {
    fun isFirstTimeLogin(): Flow<Boolean> =userPreferencesRepository.isFirstTimeLogin

    fun isLoggedIn(): Flow<Boolean> =userPreferencesRepository.isLoggedIn
}