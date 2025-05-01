package com.example.shoppingadminapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.Uri

@Composable
fun AddCategoryScreen() {


    val categoryName = remember { mutableStateOf("") }
    val cateFGoryImageUri = remember { mutableStateOf<Uri?>(null) }
    val categoryImageUrl = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = categoryName.value,
            onValueChange = { categoryName.value = it },
            label = { Text("Category Name") },
            modifier = Modifier.fillMaxWidth()

        )
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.padding(top = 16.dp)
        ){
            Text("Add Category")
        }

    }

}