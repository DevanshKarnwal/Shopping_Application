package com.example.shoppingadminapp.domain.repo

import com.example.shoppingadminapp.common.ResultState
import com.example.shoppingadminapp.domain.models.CategoryModels
import kotlinx.coroutines.flow.Flow

interface Repo {

    suspend fun addCategory(category: CategoryModels) : Flow<ResultState<String>>

}