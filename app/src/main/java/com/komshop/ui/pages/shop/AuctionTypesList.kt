package com.komshop.ui.pages.shop

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.komshop.data.Session
import com.komshop.enam.AuctionTypes
import com.komshop.navigation.Screen
import com.komshop.ui.events.AuctionEvent
import com.komshop.ui.pages.bid.AuctionEvent
import com.komshop.ui.componets.ListContainer
import com.komshop.ui.viewmodel.AuctionSelectionViewModel

@Composable
fun AuctionTypesList(
    navController: NavHostController,
    auctionSelectionViewModel: AuctionSelectionViewModel,
    auctionType: AuctionTypes
) {
    val loadingState = auctionSelectionViewModel.loadingEvent.collectAsState(initial = null).value
    val state = auctionSelectionViewModel.state

    ListContainer(
        res = loadingState,
        isEmpty = state.auctions.isEmpty(),
        onRefresh = { auctionSelectionViewModel.event(AuctionEvent.OnLoadAuctions(auctionType)) }
    ) {
        items(state.auctions) { auction ->
            AuctionEvent(auction) {
                Session.selectedAuction.value = auction
                navController.navigate(Screen.AuctionItemSelection.getRoute(auctionType))
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}