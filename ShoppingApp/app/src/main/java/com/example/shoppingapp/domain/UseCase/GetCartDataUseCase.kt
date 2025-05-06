package com.example.shoppingapp.domain.UseCase

import com.example.shoppingapp.domain.repo.Repo
import javax.inject.Inject

class GetCartDataUseCase @Inject constructor(private val repo: Repo) {
    suspend fun getCartDataUseCase() = repo.getCart()
}