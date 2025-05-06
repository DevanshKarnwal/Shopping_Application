package com.example.shoppingadminapp.presentation.nav

sealed class Routes {
    object AddCategory : Routes()
    object AddProduct : Routes()
    object HomeScreen : Routes()
}