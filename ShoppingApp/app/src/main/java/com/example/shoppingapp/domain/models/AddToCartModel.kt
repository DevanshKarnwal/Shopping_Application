package com.example.shoppingapp.domain.models

data class AddToCartModel(
    val cartId : String = "",
    val userId : String = "",
    val productId : String = "",
)
