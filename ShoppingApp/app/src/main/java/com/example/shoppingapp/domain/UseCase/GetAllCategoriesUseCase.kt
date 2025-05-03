package com.example.shoppingapp.domain.UseCase

import com.example.shoppingapp.domain.repo.Repo
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(private val repo: Repo) {
    suspend fun getAllCategoriesUseCase() = repo.getCategory()

}