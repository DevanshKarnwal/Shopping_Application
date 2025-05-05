package com.example.shoppingapp.presentation.screens.Utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.shoppingapp.domain.models.ProductDataModel

@Composable
fun ProductCart(
    product : ProductDataModel,
    onItemClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onItemClick)
    ) {

        AsyncImage(
            model = product.image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(20.dp)
                .size(120.dp)
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = product.name, maxLines = 1)
            Text(text = product.price.toString(),color = Color.Red)
            Text(text = product.finalPrice)

        }
    }
}