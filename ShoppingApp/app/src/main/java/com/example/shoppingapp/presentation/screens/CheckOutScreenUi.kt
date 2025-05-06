package com.example.shoppingapp.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shoppingapp.presentation.viewModel.MyViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.shoppingapp.presentation.nav.Routes

@Composable
fun CheckOutScreenUi(navController: NavController, viewModel: MyViewModel = hiltViewModel()) {
    val cartProducts = viewModel.cartProducts.collectAsState()
    val totalPrice = cartProducts.value.sumOf { it.finalPrice.toIntOrNull() ?: 0 }

    LaunchedEffect(key1 = Unit) {
        viewModel.loadCartProducts()
    }

    var email by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }
    var contactNumber by remember { mutableStateOf("") }
    var saveInfo by remember { mutableStateOf(false) }
    var selectedMethod by remember { mutableStateOf("Standard") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Shipping", style = MaterialTheme.typography.titleLarge)

        Text(
            text = "< Return to cart",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.clickable { navController.popBackStack() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        cartProducts.value.firstOrNull()?.let { product ->
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = product.name, fontWeight = FontWeight.Bold)
                    Text(text = "Rs: ${product.finalPrice}")
                }
                Text("Size: UK10")
                Text("Color: Red")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Sub Total", fontWeight = FontWeight.Bold)
            Text("Rs: $totalPrice", fontWeight = FontWeight.Bold)
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Shipping")
            Text("Free")
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Total", fontWeight = FontWeight.Bold)
            Text("Rs: $totalPrice", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Divider()

        Text("Contact Information", fontWeight = FontWeight.Bold)
        Text("Already have an account? Login", style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Shipping Address", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = country,
            onValueChange = { country = it },
            placeholder = { Text("Country / Region") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                placeholder = { Text("First Name") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                placeholder = { Text("Last Name") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            placeholder = { Text("Address") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                placeholder = { Text("City") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = postalCode,
                onValueChange = { postalCode = it },
                placeholder = { Text("Postal code") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = contactNumber,
            onValueChange = { contactNumber = it },
            placeholder = { Text("Contact number") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = saveInfo, onCheckedChange = { saveInfo = it })
            Spacer(modifier = Modifier.width(4.dp))
            Text("Save this information for next time")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text("Contact   Change", fontWeight = FontWeight.Medium)
        Text("Ship to   Change", fontWeight = FontWeight.Medium)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Shipping Method", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(4.dp))

        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedMethod = "Standard" }
                    .padding(vertical = 4.dp)
            ) {
                RadioButton(
                    selected = selectedMethod == "Standard",
                    onClick = { selectedMethod = "Standard" }
                )
                Text("Standard FREE delivery over Rs4500")
                Spacer(modifier = Modifier.weight(1f))
                Text("Free")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedMethod = "COD" }
                    .padding(vertical = 4.dp)
            ) {
                RadioButton(
                    selected = selectedMethod == "COD",
                    onClick = { selectedMethod = "COD" }
                )
                Text("Cash on delivery over Rs4500")
                Spacer(modifier = Modifier.weight(1f))
                Text("100")
            }
        }
    }
}
