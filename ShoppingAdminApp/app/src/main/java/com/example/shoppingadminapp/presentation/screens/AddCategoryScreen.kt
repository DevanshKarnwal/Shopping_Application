package com.example.shoppingadminapp.presentation.screens

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import coil3.compose.AsyncImage
import com.example.shoppingadminapp.domain.models.BannerModels
import com.example.shoppingadminapp.domain.models.CategoryModels
import com.example.shoppingadminapp.presentation.viewModel.MyViewModel

@Composable
fun AddCategoryScreen(viewModel: MyViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val addCategoryState = viewModel.addCategory.collectAsState()
    val addBannerState = viewModel.addBannerState.collectAsState()
    val addBannerPhotoState = viewModel.addBannerPhotoState.collectAsState()

    val photoImageUri = remember { mutableStateOf("") }
    val bannerImage = remember { mutableStateOf("") }


    val categoryName = remember { mutableStateOf("") }
    val cateGoryImageUri = remember { mutableStateOf<Uri?>(null) }
    val categoryImageUrl = remember { mutableStateOf("") }

    val bannerName = remember { mutableStateOf("") }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
            uri : android.net.Uri? ->
        if(uri != null){
            photoImageUri.value = uri.toString()
            viewModel.addBannerPhoto(uri)
        }
    }

    when{
        addBannerPhotoState.value.isLoading == true -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        addBannerPhotoState.value.error?.isNotBlank() == true -> {
            Text(text = addBannerPhotoState.value.error.toString())
        }
        addBannerPhotoState.value.isSuccess?.isNotBlank() == true -> {
            bannerImage.value = addBannerPhotoState.value.isSuccess.toString()
        }
    }

    when{
        addBannerState.value.isLoading == true -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        addBannerState.value.error?.isNotBlank() == true -> {
            Text(text = addBannerState.value.error.toString())
        }
        addBannerState.value.isSuccess?.isNotBlank() == true -> {
            Toast.makeText(context, addBannerState.value.isSuccess, Toast.LENGTH_SHORT).show()
        }
    }

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

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        if(bannerImage.value.isNotBlank()){
            AsyncImage(
                model = bannerImage.value,
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }
        Text("Add Banner")

        Button(onClick = {
            launcher.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        }) {
            Text("Add Photo")
        }

        OutlinedTextField(
            value = bannerName.value,
            onValueChange = { bannerName.value = it },
            label = { Text("Name") },
            placeholder = { Text("Name") },
            singleLine = true
        )

        Button(onClick = {
            val data = BannerModels(
                name = bannerName.value,
                imageUri = bannerImage.value.toString()
            )
            viewModel.addBanner(data)
        },
            enabled = bannerName.value.isNotBlank() && bannerImage.value.isNotBlank()

        ){
            Text("Add Banner")
        }


    }


}