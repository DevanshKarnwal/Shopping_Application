package com.example.shoppingapp.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shoppingapp.domain.models.ProductDataModel
import com.example.shoppingapp.presentation.viewModel.MyViewModel

@Composable
fun CartScreenUi(viewModel: MyViewModel = hiltViewModel(), navController: NavController) {

    val cartState = viewModel.getCartState.collectAsState()
    val cartProducts = viewModel.cartProducts.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadCartProducts()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            cartState.value.isLoaded -> {
                CircularProgressIndicator()
            }
            cartState.value.isError != null -> {
                Text(text = cartState.value.isError!!)
            }
            cartState.value.isSuccessful != null -> {
                if (cartProducts.value.isNotEmpty()) {
                    cartProducts.value.forEach { product ->
                        Text(text = product.name)
                    }
                } else {
                    Text("Cart is empty or loading product details")
                }
            }
        }
    }
}