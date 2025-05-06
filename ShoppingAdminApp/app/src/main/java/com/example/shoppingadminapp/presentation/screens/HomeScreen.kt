package com.example.shoppingadminapp.presentation.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.shoppingadminapp.presentation.nav.Routes

@Composable
fun HomeScreen(navController: NavController){

    Button(onClick = {navController.navigate(Routes.AddCategory)}) {
        Text(text = "Add Category")
    }

    Button(onClick = {navController.navigate(Routes.AddProduct)}) {
        Text(text = "Add Product")
    }


}