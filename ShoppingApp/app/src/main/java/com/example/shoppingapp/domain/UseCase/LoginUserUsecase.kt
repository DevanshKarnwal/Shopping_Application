package com.example.shoppingapp.domain.UseCase

import com.example.shoppingapp.domain.models.UserDataModels
import com.example.shoppingapp.domain.repo.Repo
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(private val repo : Repo) {
    suspend fun loginUserUseCase(userData: UserDataModels) = repo.loginWithEmailAndPassword(userData)
}