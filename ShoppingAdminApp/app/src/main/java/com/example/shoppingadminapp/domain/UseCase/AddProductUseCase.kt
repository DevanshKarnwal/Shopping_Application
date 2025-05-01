package com.example.shoppingadminapp.domain.UseCase

import com.example.shoppingadminapp.domain.models.ProductModels
import com.example.shoppingadminapp.domain.repo.Repo
import javax.inject.Inject

class AddProductUseCase @Inject constructor(private val repo: Repo) {

    suspend fun addProductUseCase(product: ProductModels) = repo.addProduct(product)

}