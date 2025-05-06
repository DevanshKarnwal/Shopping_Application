package com.example.shoppingapp.domain.UseCase

import com.example.shoppingapp.domain.repo.Repo
import javax.inject.Inject

class GetBannerUseCase @Inject constructor(private val repo: Repo) {
    suspend fun getBannerUseCase() = repo.getBanner()
}