package com.example.shoppingadminapp.domain.models

data class ProductModels(
    val name : String = "",
    val price : String = "",
    val finalPrice : String = "",
    val description : String = "",
    val image : String = "",
    val category : String = "",
    val date : Long = System.currentTimeMillis(),
    val availableUnit : Int = 0
)
