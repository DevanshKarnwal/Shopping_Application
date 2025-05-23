package com.example.shoppingapp.domain.repo

import com.example.shoppingapp.common.ResultState
import com.example.shoppingapp.domain.models.AddToCartModel
import com.example.shoppingapp.domain.models.BannerModels
import com.example.shoppingapp.domain.models.CategoryDataModels
import com.example.shoppingapp.domain.models.FavDataModel
import com.example.shoppingapp.domain.models.ProductDataModel
import com.example.shoppingapp.domain.models.UserDataModels
import kotlinx.coroutines.flow.Flow

interface Repo {

    fun registerUserWithEmailAndPassword(userData: UserDataModels) : Flow<ResultState<String>>

    fun loginWithEmailAndPassword(userData: UserDataModels) : Flow<ResultState<String>>

    fun getCategory() : Flow<ResultState<List<CategoryDataModels>>>

    fun getProducts() : Flow<ResultState<List<ProductDataModel>>>

    fun getProductById(productId : String) : Flow<ResultState<ProductDataModel>>

    fun getProductByCategory(categoryName : String) : Flow<ResultState<List<ProductDataModel>>>

    fun getUser() : Flow<ResultState<UserDataModels>>

    fun updateUserData(userData: UserDataModels) : Flow<ResultState<String>>

    fun setUserProfileImage(imageUrl : String) : Flow<ResultState<String>>

    fun addToWishList(favData : FavDataModel) : Flow<ResultState<String>>

    fun addToCart(cartData : AddToCartModel) : Flow<ResultState<String>>

    fun getCart() : Flow<ResultState<List<AddToCartModel>>>

    fun getBanner() : Flow<ResultState<List<BannerModels>>>

}