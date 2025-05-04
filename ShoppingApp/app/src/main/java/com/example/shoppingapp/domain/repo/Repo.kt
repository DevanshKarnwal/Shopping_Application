package com.example.shoppingapp.domain.repo

import com.example.shoppingapp.common.ResultState
import com.example.shoppingapp.domain.models.CategoryDataModels
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

}