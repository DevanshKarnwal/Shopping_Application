package com.example.shoppingadminapp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingadminapp.common.ResultState
import com.example.shoppingadminapp.domain.UseCase.AddCategoryUseCase
import com.example.shoppingadminapp.domain.models.CategoryModels
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class MyViewModel @Inject constructor(
    private val addCategoryUseCase: AddCategoryUseCase
) : ViewModel() {
    val _addCategory = MutableStateFlow<AddCategoryState>(AddCategoryState())
    val addCategory = _addCategory.asStateFlow()

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
                    is ResultState.Success<*> -> {
                        _addCategory.value = AddCategoryState(isSuccess = it.data.toString())
                    }
                }
            }
        }
    }


}


data class AddCategoryState(
    val isLoading: Boolean = false,
    val isSuccess: String = "",
    val error: String = ""

)