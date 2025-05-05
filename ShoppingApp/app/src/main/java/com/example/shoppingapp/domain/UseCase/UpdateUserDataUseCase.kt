package com.example.shoppingapp.domain.UseCase

import com.example.shoppingapp.domain.models.UserDataModels
import com.example.shoppingapp.domain.repo.Repo
import javax.inject.Inject

class UpdateUserDataUseCase @Inject constructor(private val repo: Repo)  {
    suspend fun updateUserDataUseCase(userData: UserDataModels) = repo.updateUserData(userData)

}