package com.komshop.ui.pages.shop

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.komshop.data.model.CartItem
import com.komshop.formatMoney
import com.komshop.ui.componets.PageBackground
import com.komshop.ui.componets.TopNav
import com.komshop.ui.dialog.getImageRequest
import com.komshop.ui.events.CheckOutEvent
import com.komshop.ui.viewmodel.CheckOutViewModel

@Composable
fun CheckOutPage(navController: NavHostController) {
    val checkOutViewModel: CheckOutViewModel = hiltViewModel()
    val state = checkOutViewModel.state

    val modifier = Modifier.padding(horizontal = 16.dp)
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = true) {
        checkOutViewModel.event(CheckOutEvent.Onload)
    }

    PageBackground(
        topBar = { TopNav(navController = navController, title = "Checkout") },
        bottomBar = { BottomCheckout() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Divider()
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp)
            ) {
                Text(
                    text = "Products",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null,
                )
            }

            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                state.items.forEach { item ->
                    CheckOutItem(item)
                }
            }

            Row(modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp)) {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "MK ${formatMoney( state.totalPrice )}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 35.dp, bottom = 8.dp)
            ) {
                Text(
                    text = "Shipping",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                )
            }

            ShippingForm(modifier, checkOutViewModel)
        }
    }
}

@Composable
fun CheckOutItem(item: CartItem) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = MaterialTheme.colorScheme.onPrimary),
        shape = RoundedCornerShape(0.dp),
        color = Color.Transparent
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = getImageRequest(url = item.image),
                modifier = Modifier
                    .size(60.dp),
                contentDescription = null
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text(
                    text = "MK ${formatMoney(item.currentBid)}",
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Qty: ${item.quantity}",
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                text = "MK ${formatMoney(item.totalPrice)}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp)
            )

        }
    }
}

@Composable
fun BottomCheckout() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onPrimary,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(bottom = 0.dp)
                .offset(y = (1).dp),
            shape = RoundedCornerShape(0.dp)
        ) {
            Text(text = "Submit Order", modifier = Modifier.padding(end = 8.dp), style = MaterialTheme.typography.titleMedium)
            Icon(imageVector = Icons.Default.Check, contentDescription = null)
        }
    }
}