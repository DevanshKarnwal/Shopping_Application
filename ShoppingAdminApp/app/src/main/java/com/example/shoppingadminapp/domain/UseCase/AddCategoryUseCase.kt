package com.example.shoppingadminapp.domain.UseCase

import com.example.shoppingadminapp.domain.models.CategoryModels
import com.example.shoppingadminapp.domain.repo.Repo
import javax.inject.Inject

class AddCategoryUseCase @Inject constructor(private val repo : Repo) {
    suspend fun addCategoryUseCase(category: CategoryModels) = repo.addCategory(category)
}