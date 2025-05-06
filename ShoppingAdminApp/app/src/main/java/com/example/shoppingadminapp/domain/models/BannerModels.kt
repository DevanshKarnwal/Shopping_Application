package com.example.shoppingadminapp.domain.models

import android.net.Uri

data class BannerModels(
    val name : String = "",
    val imageUri : String = "",
    val date : Long = System.currentTimeMillis()

)