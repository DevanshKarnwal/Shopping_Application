package com.example.shoppingapp.domain.UseCase

import com.example.shoppingapp.domain.repo.Repo
import javax.inject.Inject

class GetProductByCategoryUseCase @Inject constructor(private val repo: Repo) {
    suspend fun getProductByCategoryUseCase(categoryName: String) = repo.getProductByCategory(categoryName)
}