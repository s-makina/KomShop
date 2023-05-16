package com.komshop.ui.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.komshop.Config.selectedAuctionType
import com.komshop.R
import com.komshop.data.Session
import com.komshop.navigation.Screen
import com.komshop.ui.componets.TopNav
import com.komshop.ui.events.AuctionEvent
import com.komshop.ui.pages.bid.AuctionEvent
import com.komshop.ui.theme.orangeBg
import com.komshop.ui.viewmodel.AuctionSelectionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnlineAuction(navController: NavHostController) {
    val showConfirmDialog = remember { mutableStateOf(false) }
//    val selectedAuction = remember { mutableStateOf(auctions[0]) }
    val auctionSelectionViewModel: AuctionSelectionViewModel = hiltViewModel()
    val loadingState = auctionSelectionViewModel.loadingEvent.collectAsState(initial = null).value

    val state = auctionSelectionViewModel.state

    LaunchedEffect(key1 = true) {
        auctionSelectionViewModel.event(AuctionEvent.OnLoadAuctions(selectedAuctionType.value))
    }
//    ChangeStatusBarColor(bgColor)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = orangeBg,
        topBar = {
            TopNav(
                title = "Online Auctions",
                subtitle = "Here are the available auction",
                navController = navController
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = {
                navController.navigate(Screen.SelectAuction.route)
            }, containerColor = MaterialTheme.colorScheme.primary) {
                Icon(imageVector = Icons.Default.Language, contentDescription = null)
                Text(text = "Live Auction", modifier = Modifier.padding(start = 4.dp))
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            Icon(
                painter = painterResource(id = R.drawable.ic_decal2),
                contentDescription = null,
                modifier = Modifier.align(
                    Alignment.BottomEnd
                )
            )
        }

        Column(modifier = Modifier.padding(paddingValues)) {

            LazyColumn(modifier = Modifier) {
                items(state.auctions) { auction ->
                    AuctionEvent(auction) {
                        Session.selectedAuction.value = auction
                        navController.navigate(Screen.OnlineBidPage.route)
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
//            LazyVerticalGrid(
//                columns = GridCells.Fixed(2),
//                modifier = Modifier.padding(end = 8.dp, top = 8.dp)
//            ) {
//                items(auctions) { auction ->
//                    AuctionEvent()
////                    AuctionItem(auction) {
////                        selectedAuction.value = auction
////                        showConfirmDialog.value = true
////                    }
//                }
//            }
        }
    }
}