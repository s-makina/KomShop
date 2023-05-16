package com.komshop.ui.pages.shop

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.komshop.formatMoney
import com.komshop.navigation.Screen
import com.komshop.ui.componets.ListContainer
import com.komshop.ui.componets.PageBackground
import com.komshop.ui.componets.TopNav
import com.komshop.ui.events.CartEvents
import com.komshop.ui.pages.bid.presentation.CartUiState
import com.komshop.ui.viewmodel.CartViewModel

@Composable
fun CartPage(navController: NavHostController) {
    val cartViewModel: CartViewModel = hiltViewModel()
    val state = cartViewModel.state

    LaunchedEffect(key1 = true) {
        cartViewModel.event(CartEvents.OnLoadItems)
    }

    PageBackground(
        topBar = { TopNav(navController = navController, title = "Cart") },
        bottomBar = {
            CartBottomSummary(
                state,
                checkOut = { navController.navigate(Screen.CheckOutPage.route) })
        }
    ) {
        ListContainer(modifier = Modifier.padding(8.dp)) {
            items(state.items) { item ->
                CartItemCard(
                    item,
                    onRemove = { cartViewModel.event(CartEvents.RemoveItem(item = item)) },
                    addQty = { cartViewModel.event(CartEvents.AddQty(item)) },
                    reduceQty = { cartViewModel.event(CartEvents.ReduceQty(item)) }
                )
            }
        }
    }
}

@Composable
fun CartBottomSummary(state: CartUiState, checkOut: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text(
                text = "Total Amount",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "MK ${formatMoney(state.totalAmount)}",
                style = MaterialTheme.typography.titleMedium
            )
        }
        Divider()
        Button(
            onClick = { checkOut() },
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onPrimary,
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(text = "Checkout")
        }
    }
}