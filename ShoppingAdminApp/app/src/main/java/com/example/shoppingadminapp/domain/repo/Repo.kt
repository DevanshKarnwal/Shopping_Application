package com.example.shoppingadminapp.domain.repo

import android.net.Uri
import com.example.shoppingadminapp.common.ResultState
import com.example.shoppingadminapp.domain.models.BannerModels
import com.example.shoppingadminapp.domain.models.CategoryModels
import com.example.shoppingadminapp.domain.models.ProductModels
import kotlinx.coroutines.flow.Flow

interface Repo {

    suspend fun addCategory(category: CategoryModels) : Flow<ResultState<String>>

    suspend fun getAllCategories() : Flow<ResultState<List<CategoryModels>>>

    suspend fun addProduct(product : ProductModels) : Flow<ResultState<String>>

    suspend fun addProductPhoto(photoUri : Uri) : Flow<ResultState<String>>

    suspend fun addBanner(banner : BannerModels) : Flow<ResultState<String>>

    suspend fun addBannerPhoto(photoUri : Uri) : Flow<ResultState<String>>

}