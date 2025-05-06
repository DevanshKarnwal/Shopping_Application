package com.example.shoppingapp.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.common.ResultState
import com.example.shoppingapp.domain.UseCase.AddToCartUseCase
import com.example.shoppingapp.domain.UseCase.AddToWishListUseCase
import com.example.shoppingapp.domain.UseCase.CreateUserUseCase
import com.example.shoppingapp.domain.UseCase.GetAllCategoriesUseCase
import com.example.shoppingapp.domain.UseCase.GetAllProductsUseCase
import com.example.shoppingapp.domain.UseCase.GetBannerUseCase
import com.example.shoppingapp.domain.UseCase.GetCartDataUseCase
import com.example.shoppingapp.domain.UseCase.GetProductByCategoryUseCase
import com.example.shoppingapp.domain.UseCase.GetProductByIdUseCase
import com.example.shoppingapp.domain.UseCase.GetUserProfileImageUseCase
import com.example.shoppingapp.domain.UseCase.GetUserUseCase
import com.example.shoppingapp.domain.UseCase.LoginUserUseCase
import com.example.shoppingapp.domain.UseCase.UpdateUserDataUseCase
import com.example.shoppingapp.domain.models.AddToCartModel
import com.example.shoppingapp.domain.models.BannerModels
import com.example.shoppingapp.domain.models.CategoryDataModels
import com.example.shoppingapp.domain.models.FavDataModel
import com.example.shoppingapp.domain.models.ProductDataModel
import com.example.shoppingapp.domain.models.UserDataModels
import com.google.firebase.auth.FirebaseAuth
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
    private val getProductByCategoryUseCase: GetProductByCategoryUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val addToWishListUseCase: AddToWishListUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val updateUserDataUseCase: UpdateUserDataUseCase,
    private val updateUserProfileImageUseCase: GetUserProfileImageUseCase,
    private val getBannerUseCase: GetBannerUseCase,
    private val getCartUseCase : GetCartDataUseCase,
    private val firebaseAuth: FirebaseAuth
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

    private val _getProductByIdState = MutableStateFlow(GetProductByIdState())
    val getProductByIdState = _getProductByIdState.asStateFlow()

    private val _addToWishListState = MutableStateFlow(AddToWishListState())
    val addToWishListState = _addToWishListState.asStateFlow()

    private val _addToCartState = MutableStateFlow(AddToCartState())
    val addToCartState = _addToCartState.asStateFlow()

    private val _getUserState = MutableStateFlow(GetUserState())
    val getUserState = _getUserState.asStateFlow()

    private val _updateUserDataState = MutableStateFlow(UpdateUserDataState())
    val updateUserDataState = _updateUserDataState.asStateFlow()

    private val _updateUserProfileImageState = MutableStateFlow(UpdateUserProfileImageState())
    val updateUserProfileImageState = _updateUserProfileImageState.asStateFlow()

    private val _getBannerState = MutableStateFlow(GetBannerState())
    val getBannerState = _getBannerState.asStateFlow()

    private val _getCartState = MutableStateFlow(GetCartData())
    val getCartState = _getCartState.asStateFlow()

    private val _cartProducts = MutableStateFlow<List<ProductDataModel>>(emptyList())
    val cartProducts = _cartProducts.asStateFlow()

    var userId = ""
        private set

    fun createUser(userData: UserDataModels) {
        viewModelScope.launch(Dispatchers.IO) {
            createUserUseCase.createUserUseCase(userData).collect {
                when (it) {
                    is ResultState.Loading -> {
                        _createUserState.value = CreateUserState(isLoaded = true)
                    }

                    is ResultState.Success -> {
                        userId = firebaseAuth.currentUser?.uid.toString()
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
                        userId = firebaseAuth.currentUser?.uid.toString()
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
                        _productByCategoryState.value =
                            ProductByCategoryState(isSuccessful = it.data)
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
                getAllProductsUseCase.getAllProductsUseCase(),
                getBannerUseCase.getBannerUseCase()
            ) { categoriesState, productsState, bannerState ->
                Triple(categoriesState, productsState,bannerState)
            }.collect { (categoriesState, productsState,bannerState) ->

                Log.d("TAG Product", "getProducts: viewmodel after combine")

                when {
                    categoriesState is ResultState.Loading && productsState is ResultState.Loading && bannerState is ResultState.Loading -> {
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

                    bannerState is ResultState.Error -> {
                        _homeScreenState.value =
                            HomeScreenState(isError = bannerState.message)
                    }

                    categoriesState is ResultState.Success && productsState is ResultState.Success && bannerState is ResultState.Success -> {
                        _homeScreenState.value = HomeScreenState(
                            category = categoriesState.data,
                            product = productsState.data,
                            banner = bannerState.data
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

    fun getProductById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getProductByIdUseCase.getProductById(id).collect {
                when (it) {
                    is ResultState.Loading -> {
                        _getProductByIdState.value = GetProductByIdState(isLoaded = true)
                    }

                    is ResultState.Error -> {
                        _getProductByIdState.value = GetProductByIdState(isError = it.message)
                    }

                    is ResultState.Success -> {
                        _getProductByIdState.value = GetProductByIdState(isSuccessful = it.data)
                    }
                }
            }
        }
    }

    fun addToWishList(favData: FavDataModel) {
        viewModelScope.launch(Dispatchers.IO) {
            addToWishListUseCase.addToWishListUseCase(favData).collect {
                when (it) {
                    is ResultState.Loading -> {
                        _addToWishListState.value = AddToWishListState(isLoaded = true)
                    }

                    is ResultState.Error -> {
                        _addToWishListState.value = AddToWishListState(isError = it.message)
                    }

                    is ResultState.Success -> {
                        _addToWishListState.value = AddToWishListState(isSuccessful = it.data)
                    }
                }
            }

        }
    }

    fun addToCart(cartData: AddToCartModel) {
        viewModelScope.launch(Dispatchers.IO) {
            addToCartUseCase.addToCartUseCase(cartData).collect {
                when (it) {
                    is ResultState.Loading -> {
                        _addToCartState.value = AddToCartState(isLoaded = true)
                    }

                    is ResultState.Error -> {
                        _addToCartState.value = AddToCartState(isError = it.message)
                    }

                    is ResultState.Success -> {
                        _addToCartState.value = AddToCartState(isSuccessful = it.data)
                    }
                }
            }
        }
    }

    fun getUser(){
        viewModelScope.launch(Dispatchers.IO) {
            getUserUseCase.getUserUseCase().collect{
                when(it){
                    is ResultState.Loading -> {
                        _getUserState.value = GetUserState(isLoaded = true)
                    }
                    is ResultState.Error -> {
                        _getUserState.value = GetUserState(isError = it.message)
                    }
                    is ResultState.Success -> {
                        _getUserState.value = GetUserState(isSuccessful = it.data)
                    }
                }
            }
        }
    }

    fun updateUser(user : UserDataModels ){
        viewModelScope.launch(Dispatchers.IO) {
            updateUserDataUseCase.updateUserDataUseCase(user).collect{
                when(it){
                    is ResultState.Loading -> {
                        _updateUserDataState.value = UpdateUserDataState(isLoaded = true)
                    }
                    is ResultState.Error -> {
                        _updateUserDataState.value = UpdateUserDataState(isError = it.message)
                    }
                    is ResultState.Success -> {
                        _updateUserDataState.value = UpdateUserDataState(isSuccessful = it.data)
                    }

                }
            }
        }

    }

    fun updateUserProfileImage(imageUrl : String){
        viewModelScope.launch(Dispatchers.IO) {
            updateUserProfileImageUseCase.getUserProfileImageUseCase(imageUrl).collect{
                when(it){
                    is ResultState.Loading -> {
                        _updateUserProfileImageState.value = UpdateUserProfileImageState(isLoaded = true)
                    }
                    is ResultState.Error -> {
                        _updateUserProfileImageState.value = UpdateUserProfileImageState(isError = it.message)
                    }
                    is ResultState.Success -> {
                        _updateUserProfileImageState.value = UpdateUserProfileImageState(isSuccessful = it.data)
                    }
                }
            }
        }
    }

    fun getBanner(){
        viewModelScope.launch(Dispatchers.IO) {
            getBannerUseCase.getBannerUseCase().collect{
                when(it){
                    is ResultState.Loading -> {
                        _getBannerState.value = GetBannerState(isLoaded = true)
                    }
                    is ResultState.Error -> {
                        _getBannerState.value = GetBannerState(isError = it.message)
                    }
                    is ResultState.Success -> {
                        _getBannerState.value = GetBannerState(isSuccessful = it.data)
                    }
                }
            }
        }

    }

    fun loadCartProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val cartItemsResult = getCartUseCase.getCartDataUseCase()

            cartItemsResult.collect { result ->
                when (result) {
                    is ResultState.Loading -> {
                        _getCartState.value = GetCartData(isLoaded = true)
                    }

                    is ResultState.Error -> {
                        _getCartState.value = GetCartData(isError = result.message)
                    }

                    is ResultState.Success -> {
                        _getCartState.value = GetCartData(isSuccessful = result.data)

                        val products = mutableListOf<ProductDataModel>()
                        result.data?.forEach { cartItem ->
                            getProductByIdUseCase.getProductById(cartItem.productId).collect { prodResult ->
                                if (prodResult is ResultState.Success) {
                                    products.add(prodResult.data)
                                    _cartProducts.value = products.toList()
                                }
                            }
                        }
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
    val product: List<ProductDataModel>? = null,
    val banner: List<BannerModels>? = null
)

data class ProductByCategoryState(
    val isLoaded: Boolean = false,
    val isSuccessful: List<ProductDataModel>? = null,
    val isError: String? = null
)

data class GetProductByIdState(
    val isLoaded: Boolean = false,
    val isSuccessful: ProductDataModel? = null,
    val isError: String? = null
)

data class AddToWishListState(
    val isLoaded: Boolean = false,
    val isSuccessful: String? = null,
    val isError: String? = null
)

data class AddToCartState(
    val isLoaded: Boolean = false,
    val isSuccessful: String? = null,
    val isError: String? = null
)

data class GetUserState(
    val isLoaded: Boolean = false,
    val isSuccessful: UserDataModels? = null,
    val isError: String? = null
)

data class UpdateUserDataState(
    val isLoaded: Boolean = false,
    val isSuccessful: String? = null,
    val isError: String? = null
)

data class UpdateUserProfileImageState(
    val isLoaded: Boolean = false,
    val isSuccessful: String? = null,
    val isError: String? = null
)

data class GetBannerState(
    val isLoaded: Boolean = false,
    val isSuccessful: List<BannerModels>? = null,
    val isError: String? = null
)

data class GetCartData(
    val isLoaded: Boolean = false,
    val isSuccessful: List<AddToCartModel>?= null,
    val isError: String? = null
)