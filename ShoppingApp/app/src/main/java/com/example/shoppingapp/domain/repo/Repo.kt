package com.example.shoppingapp.domain.repo

import com.example.shoppingapp.common.ResultState
import com.example.shoppingapp.domain.models.UserDataModels
import kotlinx.coroutines.flow.Flow

interface Repo {

    fun registerUserWithEmailAndPassword(userData: UserDataModels) : Flow<ResultState<String>>

    fun loginWithEmailAndPassword(userData: UserDataModels) : Flow<ResultState<String>>

}