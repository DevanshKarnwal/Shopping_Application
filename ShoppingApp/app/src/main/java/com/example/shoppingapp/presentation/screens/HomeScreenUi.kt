package com.example.shoppingapp.presentation.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.shoppingapp.presentation.nav.Routes
import com.example.shoppingapp.presentation.screens.Utils.CategoryItem
import com.example.shoppingapp.presentation.screens.Utils.ProductCart
import com.example.shoppingapp.presentation.viewModel.MyViewModel

@Composable
fun HomeScreenUi(viewModel: MyViewModel = hiltViewModel(), navController: NavController) {
    LaunchedEffect(key1 = Unit) {
        viewModel.loadHomeScreenData()
    }
    Log.d("TAG Product", "HomeScreenUi 1:")

    val homeScreenState = viewModel.homeScreenState.collectAsState()
    Log.d("TAG Product", "HomeScreenUi:2")

    if (homeScreenState.value.isLoaded) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
            Log.d("TAG Product", "HomeScreenUi: 3")

        }

    } else if (homeScreenState.value.isError != null) {
        Log.d("TAG Product", "HomeScreenUi:4")

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = homeScreenState.value.isError.toString())
        }

    } else {
        Log.d("TAG Product", "HomeScreenUi: 5")

        val categoryList = homeScreenState.value.category ?: emptyList()
        val productList = homeScreenState.value.product ?: emptyList()
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Log.d(
                "TAG Product",
                "HomeScreenUi: 6 ${categoryList.isEmpty()} category ${productList.isEmpty()} product"
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                TextField(
                    value = "",
                    onValueChange = {},
                    placeholder = {
                        Text(text = "Search")
                    },
                    label = {Text(text = "Search")},
                    leadingIcon = {Icon(imageVector = Icons.Default.Search, contentDescription = null)},
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Default.Notifications,contentDescription = null)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = "Categories", textAlign = TextAlign.Start)
                Text(text = "See All", textAlign = TextAlign.End)
            }
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Log.d("TAG Product", "HomeScreenUi: 9")

                items(categoryList) {
                    Log.d("TAG Product", "HomeScreenUi: 7")
                    CategoryItem(imageUrl = "https://cdn-icons-png.flaticon.com/512/5499/5499206.png", categoryName = it.name) {
                        navController.navigate(
                            Routes.SeeAllProductsScreenRoute(it.name)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(productList) {
                    ProductCart(
                        product = it
                    ) {
                        navController.navigate(Routes.EachItemScreenRoute(it.productId))

                    }
                }
            }

        }


    }

}

