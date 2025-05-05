package com.example.shoppingapp.domain.UseCase

import com.example.shoppingapp.domain.repo.Repo
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(private val repo : Repo){
    suspend fun getProductById(id : String) = repo.getProductById(id)
}