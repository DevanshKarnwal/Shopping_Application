package com.example.shoppingapp.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.common.ResultState
import com.example.shoppingapp.domain.UseCase.CreateUserUseCase
import com.example.shoppingapp.domain.UseCase.GetAllCategoriesUseCase
import com.example.shoppingapp.domain.UseCase.GetAllProductsUseCase
import com.example.shoppingapp.domain.UseCase.GetProductByCategoryUseCase
import com.example.shoppingapp.domain.UseCase.LoginUserUseCase
import com.example.shoppingapp.domain.models.CategoryDataModels
import com.example.shoppingapp.domain.models.ProductDataModel
import com.example.shoppingapp.domain.models.UserDataModels
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val getAlCategoriesUseCase: GetAllCategoriesUseCase,
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val getProductByCategoryUseCase: GetProductByCategoryUseCase
) : ViewModel() {

    private val _createUserState = MutableStateFlow(CreateUserState())
    val createUserState = _createUserState.asStateFlow()

    private val _loginUserState = MutableStateFlow(LoginUserState())
    val loginUserState = _loginUserState.asStateFlow()

    private val _getAllCategoriesState = MutableStateFlow(GetAllCategories())
    val getAllCategoriesState = _getAllCategoriesState.asStateFlow()

    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState = _homeScreenState.asStateFlow()

    private val _productByCategoryState = MutableStateFlow(ProductByCategoryState())
    val productByCategoryState = _productByCategoryState.asStateFlow()

    fun createUser(userData: UserDataModels) {
        viewModelScope.launch(Dispatchers.IO) {
            createUserUseCase.createUserUseCase(userData).collect {
                when (it) {
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
                when (it) {
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

    fun getProductByCategory(categoryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getProductByCategoryUseCase.getProductByCategoryUseCase(categoryName).collect {
                when (it) {
                    ResultState.Loading -> {
                        _productByCategoryState.value = ProductByCategoryState(isLoaded = true)
                    }
                    is ResultState.Error -> {
                        _productByCategoryState.value = ProductByCategoryState(isError = it.message)
                    }
                    is ResultState.Success -> {
                        _productByCategoryState.value = ProductByCategoryState(isSuccessful = it.data)
                    }

                }

            }
        }

    }

    fun loadHomeScreenData() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("TAG Product", "getProducts: viewmodel before combine")

            combine(
                getAlCategoriesUseCase.getAllCategoriesUseCase(),
                getAllProductsUseCase.getAllProductsUseCase()
            ) { categoriesState, productsState ->
                Pair(categoriesState, productsState)
            }.collect { (categoriesState, productsState) ->

                Log.d("TAG Product", "getProducts: viewmodel after combine")

                when {
                    categoriesState is ResultState.Loading && productsState is ResultState.Loading -> {
                        _homeScreenState.value = HomeScreenState(isLoaded = true)
                    }
                    categoriesState is ResultState.Error -> {
                        _homeScreenState.value =
                            HomeScreenState(isError = categoriesState.message)
                    }

                    productsState is ResultState.Error -> {
                        _homeScreenState.value =
                            HomeScreenState(isError = productsState.message)
                    }

                    categoriesState is ResultState.Success && productsState is ResultState.Success -> {
                        _homeScreenState.value = HomeScreenState(
                            category = categoriesState.data,
                            product = productsState.data
                        )
                    }


                }
            }
        }
    }

    fun getAllCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            getAlCategoriesUseCase.getAllCategoriesUseCase().collect {
                when (it) {
                    is ResultState.Loading -> {
                        _getAllCategoriesState.value = GetAllCategories(isLoaded = true)
                    }

                    is ResultState.Error -> {
                        _getAllCategoriesState.value = GetAllCategories(isError = it.message)
                    }

                    is ResultState.Success -> {
                        _getAllCategoriesState.value = GetAllCategories(isSuccessful = it.data)
                    }
                }
            }
        }
    }

}


data class CreateUserState(
    val isLoaded: Boolean = false,
    val isSuccessful: String? = null,
    val isError: String? = null
)

data class LoginUserState(
    val isLoaded: Boolean = false,
    val isSuccessful: String? = null,
    val isError: String? = null
)

data class GetAllCategories(
    val isLoaded: Boolean = false,
    val isSuccessful: List<CategoryDataModels>? = null,
    val isError: String? = null
)

data class HomeScreenState(
    val isLoaded: Boolean = false,
    val isError: String? = null,
    val category: List<CategoryDataModels>? = null,
    val product: List<ProductDataModel>? = null
)
data class ProductByCategoryState(
    val isLoaded: Boolean = false,
    val isSuccessful: List<ProductDataModel>? = null,
    val isError: String? = null
)