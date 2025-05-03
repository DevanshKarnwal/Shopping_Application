package com.example.shoppingadminapp.presentation.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppingadminapp.domain.models.ProductModels
import com.example.shoppingadminapp.presentation.viewModel.MyViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(viewModel: MyViewModel = hiltViewModel()) {

    val getAllCategory = viewModel.getAllCategory.collectAsState()
    var fetchedCategoryList = getAllCategory.value.isSuccess
    val productState = viewModel.addProduct.collectAsState()
    val context = LocalContext.current

    var expanded by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf(0) }
    var finalPrice by remember { mutableStateOf(0) }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var availableUnit by remember { mutableStateOf(0) }
    val photoImageUri = remember { mutableStateOf("") }
    val productImage = remember { mutableStateOf("") }

    val addProductPhotoState = viewModel.addProductPhotoState.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        uri : Uri? ->
            if(uri != null){
                photoImageUri.value = uri.toString()
                viewModel.addProductPhoto(uri)
            }
    }


    LaunchedEffect(1) {
        viewModel.getAllCategory()
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {

            getAllCategory.value.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            getAllCategory.value.error?.isNotBlank() == true -> {
                Text(text = getAllCategory.value.error.toString())
            }

            getAllCategory.value.isSuccess != null -> {
                fetchedCategoryList = getAllCategory.value.isSuccess
            }
        }

        when {
            productState.value.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            productState.value.error?.isNotBlank() == true -> {
                Text(text = productState.value.error.toString())
            }

            productState.value.isSuccess?.isNotBlank() == true -> {
                Toast.makeText(context, productState.value.isSuccess, Toast.LENGTH_SHORT).show()
            }
        }

        when{
            addProductPhotoState.value.isLoading == true -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            addProductPhotoState.value.error?.isNotBlank() == true -> {
                Text(text = addProductPhotoState.value.error.toString())
            }
            addProductPhotoState.value.isSuccess?.isNotBlank() == true -> {
                productImage.value = addProductPhotoState.value.isSuccess.toString()
            }
        }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = category,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select Category") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                fetchedCategoryList?.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        text = { Text("${item.name} - ${item.name}") },
                        onClick = {
                            category = item.name
                            expanded = false
                        }
                    )
                }
            }
        }
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            placeholder = { Text("Name") },
            singleLine = true
        )
        OutlinedTextField(
            value = price.toString(),
            onValueChange = { price = it.toInt() },
            label = { Text("Price") },
            placeholder = { Text("Price") },
            singleLine = true
        )
        OutlinedTextField(
            value = finalPrice.toString(),
            onValueChange = { finalPrice = it.toInt() },
            label = { Text("Final Price") },
            placeholder = { Text("Final Price") },
            singleLine = true
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("description") },
            placeholder = { Text("description") },
        )
        OutlinedTextField(
            value = availableUnit.toString(),
            onValueChange = { availableUnit = it.toInt() },
            label = { Text("Available Unit") },
            placeholder = { Text("Available Unit") },
            singleLine = true
        )
        Button(onClick = {
            launcher.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        }) {
            Text("Add Photo")
        }
        Button(
            onClick = {
                val data = ProductModels(
                    name = name,
                    price = price.toString(),
                    finalPrice = finalPrice.toString(),
                    description = description,
                    category = category,
                    availableUnit = availableUnit,
                    image = productImage.value
                )
                viewModel.addProduct(data)
                name = ""
                price = 0
                finalPrice = 0
                description = ""
                category = ""
                availableUnit = 0
                productImage.value = ""
                photoImageUri.value = ""
            },
            enabled = name.isNotBlank()
                    && price > 0
                    && finalPrice > 0
                    && description.isNotBlank()
                    && category.isNotBlank()
                    && availableUnit > 0
                    && productImage.value.isNotBlank()

        ) {
            Text("Add Product")
        }


    }

}