package com.example.shoppingapp.domain.UseCase

import com.example.shoppingapp.domain.models.AddToCartModel
import com.example.shoppingapp.domain.repo.Repo
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(private val repo: Repo)  {
    suspend fun addToCartUseCase(cartData: AddToCartModel) = repo.addToCart(cartData)

}