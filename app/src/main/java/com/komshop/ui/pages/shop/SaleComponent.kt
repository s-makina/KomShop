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
import com.komshop.formatMoney
import com.komshop.ui.dialog.getImageRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaleComponent(
    item: AuctionItem,
    isAuction: Boolean = true,
    addToCart: () -> Unit = {},
    placeABid: () -> Unit = {},
    onClick: () -> Unit,
) {
    Card(
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
                    model = getImageRequest(url = item.image),
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
                val price = if (isAuction) "Bid" else "Price"
                Text(
                    text = "$price: MK ${formatMoney(item.currentBid)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                val cart = if (isAuction) "Place a bid" else "Add to cart"
                Button(onClick = {
                    if (isAuction) {
                        placeABid()
                    } else {
                        addToCart()
                    }
                }) {
                    Text(text = cart)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Badge(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp),
//                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Text(text = "Open")
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

