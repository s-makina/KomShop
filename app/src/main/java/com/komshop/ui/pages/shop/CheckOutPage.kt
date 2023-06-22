package com.komshop.ui.pages.shop

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.komshop.Config.WHATSAPP_NUMBER
import com.komshop.data.Session.processing
import com.komshop.data.getTotalPrice
import com.komshop.data.model.CartItem
import com.komshop.formatMoney
import com.komshop.navigation.Screen
import com.komshop.sendWapMsg
import com.komshop.ui.componets.PageBackground
import com.komshop.ui.componets.TopNav
import com.komshop.ui.dialog.Alert
import com.komshop.ui.dialog.AlertType
import com.komshop.ui.dialog.getImageRequest
import com.komshop.ui.events.CheckOutEvent
import com.komshop.ui.viewmodel.CheckOutViewModel
import com.komshop.util.Status
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CheckOutPage(navController: NavHostController) {
    val checkOutViewModel: CheckOutViewModel = hiltViewModel()
    val state = checkOutViewModel.state

    val modifier = Modifier.padding(horizontal = 16.dp)
    val scrollState = rememberScrollState()
    val loadingState = checkOutViewModel.submittingOrder.collectAsStateWithLifecycle(initialValue = null).value

    LaunchedEffect(key1 = true) {
        checkOutViewModel.event(CheckOutEvent.Onload)
    }

    PageBackground(
        topBar = { TopNav(navController = navController, title = "Checkout") },
        bottomBar = {
            BottomCheckout(checkOutViewModel)
        }
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

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "MK ${formatMoney(state.totalPrice)}",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
//
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 35.dp, bottom = 8.dp)
            ) {
                Text(
                    text = "Contact Details",
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

    val coroutine = rememberCoroutineScope()
    
    when (loadingState?.status) {
        Status.LOADING -> {
            processing.value = true
        }
        Status.SUCCESS -> {
            processing.value = false
            Alert(content = "Your Order have been submitted", alertType = AlertType.SUCCESS)
            LaunchedEffect(key1 = true ) {
                delay(2000)
                navController.navigate(Screen.AuctionItemSelection.route)
            }
        }
        Status.ERROR -> {
            processing.value = false
            Alert(content = loadingState.message ?: "", alertType = AlertType.ERROR)
        }
        else -> Unit
    }
}

@Composable
fun CheckOutItem(item: CartItem) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = MaterialTheme.colorScheme.onSurface),
        shape = RoundedCornerShape(0.dp),
        color = Color.Transparent
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = getImageRequest(url = item.images.first().src),
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
                    text = "MK ${formatMoney(item.price.toDouble())}",
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Qty: ${item.quantity}",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                text = "MK ${formatMoney(item.getTotalPrice())}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp)
            )

        }
    }
}

@Composable
fun BottomCheckout(checkOutViewModel: CheckOutViewModel) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = {
                checkOutViewModel.event(CheckOutEvent.OnSubmitWhatsapp(context))
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .offset(y = (1).dp),
//            shape = RoundedCornerShape(0.dp)
        ) {
            Icon(imageVector = Icons.Default.Whatsapp, contentDescription = null)
            Text(
                text = "Whatsapp",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 8.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }

        Button(
            onClick = {
                checkOutViewModel.event(CheckOutEvent.OnSubmit)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onPrimary,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .fillMaxWidth()
//                .height(60.dp)
                .weight(1f)
                .offset(y = (1).dp),
//            shape = RoundedCornerShape(0.dp)
        ) {
            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null)
            Text(
                text = "Submit Order",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 8.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}