package com.example.shoppingadminapp.domain.UseCase

import com.example.shoppingadminapp.domain.repo.Repo
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject() constructor(private val repo : Repo) {
    suspend fun getAllCategoriesUseCase() = repo.getAllCategories()
}