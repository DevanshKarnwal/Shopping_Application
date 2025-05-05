package com.example.shoppingapp.domain.UseCase

import com.example.shoppingapp.domain.models.FavDataModel
import com.example.shoppingapp.domain.repo.Repo
import javax.inject.Inject

class AddToWishListUseCase @Inject constructor(private val repo: Repo) {
    suspend fun addToWishListUseCase(favData: FavDataModel) = repo.addToWishList(favData)
}