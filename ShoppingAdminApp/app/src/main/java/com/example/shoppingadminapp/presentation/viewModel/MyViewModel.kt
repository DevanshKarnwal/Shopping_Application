package com.example.shoppingadminapp.presentation.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingadminapp.common.ResultState
import com.example.shoppingadminapp.domain.UseCase.AddBannerImageUseCase
import com.example.shoppingadminapp.domain.UseCase.AddBannerUseCase
import com.example.shoppingadminapp.domain.UseCase.AddCategoryUseCase
import com.example.shoppingadminapp.domain.UseCase.AddProductPhotoUseCase
import com.example.shoppingadminapp.domain.UseCase.AddProductUseCase
import com.example.shoppingadminapp.domain.UseCase.GetAllCategoriesUseCase
import com.example.shoppingadminapp.domain.models.BannerModels
import com.example.shoppingadminapp.domain.models.CategoryModels
import com.example.shoppingadminapp.domain.models.ProductModels
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val addCategoryUseCase: AddCategoryUseCase,
    private val getAllCategoryUseCase: GetAllCategoriesUseCase,
    private val addProductUseCase: AddProductUseCase,
    private val addProductPhotoUseCase: AddProductPhotoUseCase,
    private val addBannerUseCase: AddBannerUseCase,
    private val addBannerImageUseCase: AddBannerImageUseCase
) : ViewModel() {
    private val _addCategory = MutableStateFlow(AddCategoryState())
    val addCategory = _addCategory.asStateFlow()

    private  val _getAllCategory = MutableStateFlow(GetAllCategorySate())
    val getAllCategory = _getAllCategory.asStateFlow()

    private val _addProduct = MutableStateFlow(AddProductState())
    val addProduct = _addProduct.asStateFlow()

    private val _addProductPhotoState = MutableStateFlow(AddProductPhotoState())
    val addProductPhotoState = _addProductPhotoState.asStateFlow()

    private val _addBanner = MutableStateFlow(AddBannerState())
    val addBannerState = _addBanner.asStateFlow()

    private val _addBannerPhotoState = MutableStateFlow(AddBannerPhotoState())
    val addBannerPhotoState = _addBannerPhotoState.asStateFlow()

    fun addCategory(category: CategoryModels) {
        viewModelScope.launch(Dispatchers.IO) {
            addCategoryUseCase.addCategoryUseCase(category).collect {
                when (it) {
                    is ResultState.Error -> {
                        _addCategory.value = AddCategoryState(error = it.message)
                    }

                    ResultState.Loading -> {
                        _addCategory.value = AddCategoryState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _addCategory.value = AddCategoryState(isSuccess = it.data.toString())
                    }
                }
            }
        }
    }

    fun getAllCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllCategoryUseCase.getAllCategoriesUseCase().collect {
                when (it) {
                    is ResultState.Error -> {
                        _getAllCategory.value = GetAllCategorySate(error = it.message)
                    }
                    is ResultState.Loading -> {
                        _getAllCategory.value = GetAllCategorySate(isLoading = true)
                    }
                    is ResultState.Success ->{
                        _getAllCategory.value = GetAllCategorySate(isSuccess = it.data)
                    }
                }
            }
        }
    }

    fun addProduct(product: ProductModels) {
       viewModelScope.launch(Dispatchers.IO) {
           addProductUseCase.addProductUseCase(product).collect {
                when(it){
                    is ResultState.Error -> {
                        _addProduct.value = AddProductState(error = it.message)
                    }
                    ResultState.Loading -> {
                        _addProduct.value = AddProductState(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _addProduct.value = AddProductState(isSuccess = it.data.toString())
                    }
                }
           }
       }
    }

    fun addProductPhoto(photoUri: Uri){
        viewModelScope.launch(Dispatchers.IO) {
            addProductPhotoUseCase.addProductPhotoUseCase(photoUri).collect{
                when(it){
                    is ResultState.Error -> {
                        _addProductPhotoState.value = AddProductPhotoState(error = it.message)
                    }
                    ResultState.Loading -> {
                        _addProductPhotoState.value = AddProductPhotoState(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _addProductPhotoState.value = AddProductPhotoState(isSuccess = it.data.toString())
                    }
                }
            }
        }
    }

    fun addBanner(banner : BannerModels){
        viewModelScope.launch(Dispatchers.IO) {
            addBannerUseCase.addBannerUseCase(banner).collect{
                when(it){
                    is ResultState.Error -> {
                        _addBanner.value = AddBannerState(error = it.message)
                    }
                    ResultState.Loading -> {
                        _addBanner.value = AddBannerState(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _addBanner.value = AddBannerState(isSuccess = it.data.toString())
                    }
                }
            }
        }
    }

    fun addBannerPhoto(photoUri: Uri){
        viewModelScope.launch(Dispatchers.IO){
            addBannerImageUseCase.addBannerImageUseCase(photoUri).collect{
                when(it){
                    is ResultState.Error -> {
                        _addBannerPhotoState.value = AddBannerPhotoState(error = it.message)
                    }
                    ResultState.Loading -> {
                        _addBannerPhotoState.value = AddBannerPhotoState(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _addBannerPhotoState.value = AddBannerPhotoState(isSuccess = it.data.toString())
                    }
                }
            }

        }
    }

}


data class AddCategoryState(
    val isLoading: Boolean = false,
    val isSuccess: String? = null,
    val error: String? = null
)

data class GetAllCategorySate(
    val isLoading: Boolean = false,
    val isSuccess: List<CategoryModels>? = null,
    val error: String? = null
)

data class AddProductState(
    val isLoading: Boolean = false,
    val isSuccess: String? = null,
    val error: String? = null
)

data class AddProductPhotoState(
    val isLoading: Boolean = false,
    val isSuccess: String? = null,
    val error: String? = null
)

data class AddBannerState(
    val isLoading: Boolean = false,
    val isSuccess: String? = null,
    val error: String? = null
)

data class AddBannerPhotoState(
    val isLoading: Boolean = false,
    val isSuccess: String? = null,
    val error: String? = null
)
