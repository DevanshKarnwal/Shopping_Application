package com.example.shoppingapp.domain.models

data class BannerModels(
    val name : String = "",
    val imageUri : String = "",
    val date : Long = System.currentTimeMillis()

)