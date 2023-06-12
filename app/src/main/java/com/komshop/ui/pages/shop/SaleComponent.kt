package com.komshop.ui.pages.shop

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.komshop.data.model.AuctionItem
import com.komshop.data.model.Product
import com.komshop.formatMoney
import com.komshop.ui.dialog.getImageRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductComponent(
    item: Product,
    addToCart: () -> Unit = {},
    onClick: () -> Unit,
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, bottom = 8.dp),
        onClick = {
            onClick()
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp), contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = getImageRequest(url = item.images.firstOrNull()?.src ?: ""),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = item.name,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )

                Text(
                    text = "price: MK ${formatMoney(item.salePrice.toDouble())}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Button(onClick = {
                    addToCart()
                }) {
                    Text(text = "Add to cart")
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (item.on_sale) {
                Badge(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp),
//                containerColor = MaterialTheme.colorScheme.secondary
                ) {
                    Text(text = "Sale")
                }
            }

        }
    }
}

@Composable
fun SaleDropDown(expanded: MutableState<Boolean>, onClick: (String) -> Unit = {}) {
    DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
        DropdownMenuItem(
            onClick = { },
            leadingIcon = {
                Icon(imageVector = Icons.Default.AddShoppingCart, contentDescription = null)
            },
            text = { Text(text = "Add to cart") })
//        DropdownMenuItem(
//            onClick = { /*TODO*/ },
//            leadingIcon = {
//                Icon(imageVector = Icons.Default.ShoppingCartCheckout, contentDescription = null)
//            },
//            text = { Text(text = "Checkout") })
//        DropdownMenuItem(onClick = { /*TODO*/ }, leadingIcon = {
//            Icon(imageVector = Icons.Default.Share, contentDescription = null)
//        }, text = { Text(text = "Share") })
    }
}

