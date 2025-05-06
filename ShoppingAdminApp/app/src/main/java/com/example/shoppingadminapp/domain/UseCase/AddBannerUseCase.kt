package com.example.shoppingadminapp.domain.UseCase

import com.example.shoppingadminapp.domain.models.BannerModels
import com.example.shoppingadminapp.domain.repo.Repo
import javax.inject.Inject

class AddBannerUseCase @Inject constructor(private val repo: Repo) {
    suspend fun addBannerUseCase(banner: BannerModels) = repo.addBanner(banner)
}