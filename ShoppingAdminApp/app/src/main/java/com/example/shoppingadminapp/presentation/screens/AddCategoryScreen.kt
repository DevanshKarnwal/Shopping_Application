package com.example.shoppingadminapp.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.Uri
import com.example.shoppingadminapp.domain.models.CategoryModels
import com.example.shoppingadminapp.presentation.viewModel.MyViewModel

@Composable
fun AddCategoryScreen(viewModel: MyViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val addCategoryState = viewModel.addCategory.collectAsState()
    when {
        addCategoryState.value.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        addCategoryState.value.error?.isNotBlank() == true -> {
            Text(text = addCategoryState.value.error.toString())
        }

        addCategoryState.value.isSuccess?.isNotBlank() == true -> {
            Toast.makeText(context, addCategoryState.value.isSuccess, Toast.LENGTH_SHORT).show()
        }
    }

    val categoryName = remember { mutableStateOf("") }
    val cateFGoryImageUri = remember { mutableStateOf<Uri?>(null) }
    val categoryImageUrl = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
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
            onClick = {
                val data = CategoryModels(name = categoryName.value)
                viewModel.addCategory(data)
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Add Category")
        }

    }

}