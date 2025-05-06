package com.example.shoppingapp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.shoppingapp.presentation.nav.Routes
import com.example.shoppingapp.presentation.viewModel.MyViewModel

@Composable
fun CartScreenUi(viewModel: MyViewModel = hiltViewModel(), navController: NavController) {
    val cartState = viewModel.getCartState.collectAsState()
    val cartProducts = viewModel.cartProducts.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadCartProducts()
    }

    val totalPrice = remember(cartProducts.value) {
        cartProducts.value.sumOf { it.finalPrice.toIntOrNull() ?: 0 }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "Shopping Cart", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "< Continue Shopping",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.clickable { navController.popBackStack() }
        )
        Spacer(modifier = Modifier.height(12.dp))

        when {
            cartState.value.isLoaded -> {
                CircularProgressIndicator()
            }

            cartState.value.isError != null -> {
                Text(text = cartState.value.isError!!)
            }

            cartState.value.isSuccessful != null -> {
                if (cartProducts.value.isEmpty()) {
                    Text("Your cart is empty.")
                } else {
                    cartProducts.value.forEach { product ->
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = product.image,
                                contentDescription = product.name,
                                modifier = Modifier.size(100.dp)
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = product.name, fontWeight = FontWeight.Bold)
                                Text(text = "Code: ${product.productId}")
                                Text(text = "Size: UK10")
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("Color: ")
                                    Box(
                                        modifier = Modifier
                                            .size(12.dp)
                                            .background(Color.Red)
                                    )
                                }
                            }

                            Column(
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(text = "Rs: ${product.price}")
                                Text(text = "1")
                                Text(text = "Rs: ${product.finalPrice}")
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Sub Total:", fontWeight = FontWeight.Bold)
                        Text("Rs: $totalPrice", fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            navController.navigate(Routes.CheckOutScreenRoute)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text("Checkout")
                    }
                }
            }
        }
    }
}
