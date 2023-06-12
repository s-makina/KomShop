package com.komshop.ui.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.komshop.navigation.Screen
import com.komshop.ui.componets.GridContainerComponent
import com.komshop.ui.componets.PageBackground
import com.komshop.ui.componets.TopNav
import com.komshop.ui.events.ProductListEvents
import com.komshop.ui.pages.shop.MyAuctionItem
import com.komshop.ui.viewmodel.ProductListViewModel
import com.komshop.util.CodeGeneratorHelper
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowMyBids(navController: NavHostController) {
    val productListViewModel: ProductListViewModel = hiltViewModel()
    val state = productListViewModel.state
    val coroutineScope = rememberCoroutineScope()

    val showBidDialog = remember {
        mutableStateOf(false)
    }

    val loadingState = productListViewModel.loadingEvent.collectAsState(initial = null).value
    val placeBidEventState = productListViewModel.placeBidEvent.collectAsState(initial = null).value

    val showDialog = remember { mutableStateOf(false) }
    val bitMap = remember {
        CodeGeneratorHelper.generateQRCode(content = "1234567")
    }

    LaunchedEffect(key1 = true) {
        productListViewModel.event(ProductListEvents.OnLoadMyBids)
    }

    PageBackground(
        topBar = {
            TopNav(
                navController = navController,
                title = "My Bids",
                subtitle = ""
            )
        }
    ) {
        GridContainerComponent(
            res = loadingState,
            isEmpty = state.products.isEmpty(),
            onRefresh = { productListViewModel.event(ProductListEvents.OnLoadMyBids) }
        ){
            items(state.products) { item ->
                MyAuctionItem(item, onPreBid = {

                }) {
//                    Session.currentAuctionItem.value = item
                    coroutineScope.launch {
                        navController.navigate(Screen.OnlineBidPreview.route)
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}