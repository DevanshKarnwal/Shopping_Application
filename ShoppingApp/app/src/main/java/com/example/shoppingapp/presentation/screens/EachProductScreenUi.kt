package com.example.shoppingapp.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.shoppingapp.domain.models.FavDataModel
import com.example.shoppingapp.presentation.nav.Routes
import com.example.shoppingapp.presentation.viewModel.MyViewModel

@Composable
fun EachProductScreenUi(
    id: String,
    viewModel: MyViewModel = hiltViewModel(),
    navController: NavController
) {

    val productState = viewModel.getProductByIdState.collectAsState()
    val addToWishListState = viewModel.addToWishListState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        viewModel.getProductById(id)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        when{
            addToWishListState.value.isLoaded -> {
                CircularProgressIndicator()
            }
            addToWishListState.value.isError != null -> {
                Text(text = addToWishListState.value.isError!!)
            }
            addToWishListState.value.isSuccessful != null -> {
                Toast.makeText(context, addToWishListState.value.isSuccessful, Toast.LENGTH_SHORT).show()
            }
        }

        when {
            productState.value.isLoaded -> {
                CircularProgressIndicator()
            }

            productState.value.isError != null -> {
                Text(text = productState.value.isError!!)
            }

            productState.value.isSuccessful != null -> {
                AsyncImage(
                    model = productState.value.isSuccessful?.image,
                    contentDescription = null
                )
                Text(text = productState.value.isSuccessful?.name ?: " ")
                Text(text = productState.value.isSuccessful?.description ?: " ")
                Text(text = productState.value.isSuccessful?.price.toString())
                Text(text = productState.value.isSuccessful?.finalPrice.toString())

                Button(onClick = {
                    navController.navigate(Routes.CheckOutScreenRoute)
                }) {
                    Text("Buy Now")
                }
                Button(onClick = {
                    navController.navigate(Routes.CartScreenRoute)
                }) {
                    Text("Add to Cart")
                }
                Button(onClick = {
                    val data = FavDataModel(
                        productId = id,
                        userId = viewModel.userId
                    )
                    viewModel.addToWishList(data)
                }) {
                    Text("Wish List")
                }

            }
        }
    }

}