package com.example.shoppingapp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.common.ResultState
import com.example.shoppingapp.domain.UseCase.CreateUserUseCase
import com.example.shoppingapp.domain.UseCase.LoginUserUseCase
import com.example.shoppingapp.domain.models.UserDataModels
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {

    val _createUserState = MutableStateFlow(CreateUserState())
    val createUserState = _createUserState.asStateFlow()

    val _loginUserState = MutableStateFlow(LoginUserState())
    val loginUserState = _loginUserState.asStateFlow()

    fun createUser(userData: UserDataModels) {
        viewModelScope.launch(Dispatchers.IO) {
            createUserUseCase.createUserUseCase(userData).collect {
                when(it){
                    is ResultState.Loading -> {
                        _createUserState.value = CreateUserState(isLoaded = true)
                    }
                    is ResultState.Success -> {
                        _createUserState.value = CreateUserState(isSuccessful = it.data)
                    }
                    is ResultState.Error -> {
                        _createUserState.value = CreateUserState(isError = it.message)
                    }
                }
            }
        }
    }

    fun loginUser(userData: UserDataModels) {
        viewModelScope.launch(Dispatchers.IO) {
            loginUserUseCase.loginUserUseCase(userData).collect {
                when(it){
                    is ResultState.Loading -> {
                        _loginUserState.value = LoginUserState(isLoaded = true)
                    }
                    is ResultState.Success -> {
                        _loginUserState.value = LoginUserState(isSuccessful = it.data)
                    }
                    is ResultState.Error -> {
                        _loginUserState.value = LoginUserState(isError = it.message)
                    }
                }

            }
        }

    }

}


data class CreateUserState(
    val isLoaded: Boolean = false,
    val isSuccessful: String ? = null,
    val isError: String? = null
)

data class LoginUserState(
    val isLoaded: Boolean = false,
    val isSuccessful: String ? = null,
    val isError: String? = null
)