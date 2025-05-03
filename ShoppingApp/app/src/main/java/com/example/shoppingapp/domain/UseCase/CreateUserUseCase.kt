package com.example.shoppingapp.domain.UseCase

import com.example.shoppingapp.domain.models.UserDataModels
import com.example.shoppingapp.domain.repo.Repo
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(private val repo : Repo) {
    suspend fun createUserUseCase(userData: UserDataModels) = repo.registerUserWithEmailAndPassword(userData)

}