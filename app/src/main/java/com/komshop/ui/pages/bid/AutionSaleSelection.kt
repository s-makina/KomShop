package com.komshop.ui.pages.bid

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.skydoves.landscapist.coil.CoilImage
import com.komshop.data.Session.selectedAuction
import com.komshop.data.model.AuctionType
import com.komshop.enam.AuctionTypes
import com.komshop.navigation.Screen
import com.komshop.ui.componets.AuctionPageBackGround
import com.komshop.ui.componets.SegmentedButton
import com.komshop.ui.componets.TopNav
import com.komshop.ui.dialog.ConfirmDaialog
import com.komshop.ui.events.AuctionEvent
import com.komshop.ui.pages.shop.AuctionTypesList
import com.komshop.ui.viewmodel.AuctionSelectionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuctionTypeSelection(navController: NavHostController, auctionTypes: AuctionTypes) {
    val showConfirmDialog = remember { mutableStateOf(false) }
    val auctionSelectionViewModel: AuctionSelectionViewModel = hiltViewModel()

    LaunchedEffect(key1 = auctionTypes) {
        auctionSelectionViewModel.event(AuctionEvent.OnLoadAuctions(auctionTypes))
    }

    AuctionPageBackGround(
        topBar = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                TopNav(
                    title = auctionTypes.pageTitle,
                    navController = navController
                )
                val itemsList = listOf("Live Auction", "Online Auctions")

                if (auctionTypes != AuctionTypes.CASH_AUCTION)
                    SegmentedButton(itemsList) {
                        if (it == 0) {
                            navController.navigate(Screen.SelectAuction.getRoute(AuctionTypes.LIVE_AUCTION)) {
                                launchSingleTop = true
                            }
                        } else {
                            navController.navigate(Screen.SelectAuction.getRoute(AuctionTypes.ONLINE_AUCTION)) {
                                launchSingleTop = true
                            }
                        }
                    }
            }
        },
        auctionTypes = auctionTypes,
        floatingActionButton = {
//            ExtendedFloatingActionButton(onClick = {
//                if (auctionTypes == AuctionTypes.LIVE_AUCTION) {
//                    navController.navigate(Screen.SelectAuction.getRoute(AuctionTypes.ONLINE_AUCTION)) {
//                        launchSingleTop = true
//                    }
//                } else {
//                    navController.navigate(Screen.SelectAuction.getRoute(AuctionTypes.LIVE_AUCTION)) {
//                        launchSingleTop = true
//                    }
//                }
//            }, containerColor = onBlue) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_auction),
//                    contentDescription = null
//                )
//                Text(text = "Online Auction", modifier = Modifier.padding(start = 4.dp))
//            }

        }
    ) {
        AuctionTypesList(navController, auctionSelectionViewModel, auctionTypes)
        ConfirmDaialog(
            showDialog = showConfirmDialog,
            title = selectedAuction.value?.name ?: "",
            message = "Would you like to register to this auction?",
            onProceed = {
//                navController.navigate(Screen.AuctionItemSelection.getRoute())
            }
        )
    }
//    DatePickerUi(dateState)


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuctionEvent(auction: AuctionType, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 10.dp, end = 10.dp),
        onClick = { onClick() }
    ) {
        CoilImage(
            imageModel = auction.image,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = auction.name,
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp)
            )

            FilterChip(onClick = { /*TODO*/ }, label = {
                Text(text = "${auction.dateFrom} > ${auction.dateTo}")
            }, selected = true)
        }
    }
}