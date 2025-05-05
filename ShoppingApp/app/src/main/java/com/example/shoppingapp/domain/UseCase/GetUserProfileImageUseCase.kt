package com.example.shoppingapp.domain.UseCase

import com.example.shoppingapp.domain.repo.Repo
import javax.inject.Inject

class GetUserProfileImageUseCase @Inject constructor(private val repo: Repo) {
    suspend fun getUserProfileImageUseCase(imageUrl: String) = repo.setUserProfileImage(imageUrl)
}