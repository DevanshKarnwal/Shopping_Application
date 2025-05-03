package com.example.shoppingapp.domain.models

data class ProductDataModel(
    val name : String = "",
    val description : String = "",
    val price : String = "",
    val finalPrice : String = "",
    val category : String = "",
    val image : String = "",
    val availableUnit : Int = 0,
    var productId : String = "",

    )
