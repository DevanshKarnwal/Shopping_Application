package com.example.shoppingapp.domain.UseCase

import com.example.shoppingapp.domain.repo.Repo
import javax.inject.Inject


class GetAllProductsUseCase @Inject constructor(private val repo: Repo)  {
    suspend fun getAllProductsUseCase() = repo.getProducts()

}