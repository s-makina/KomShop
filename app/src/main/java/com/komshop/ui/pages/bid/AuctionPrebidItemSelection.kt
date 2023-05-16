package com.komshop.ui.pages.bid

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.skydoves.landscapist.coil.CoilImage
import com.komshop.data.Session.currentAuctionItem
import com.komshop.data.model.AuctionItem
import com.komshop.data.model.AuctionType
import com.komshop.enam.AuctionTypes
import com.komshop.formatMoney
import com.komshop.navigation.Screen
import com.komshop.ui.componets.GridContainerComponent
import com.komshop.ui.componets.PageBackground
import com.komshop.ui.componets.TopNav
import com.komshop.ui.dialog.PrebidDialog
import com.komshop.ui.events.AuctionItemEvents
import com.komshop.ui.events.BiddingEvents
import com.komshop.ui.pages.shop.SaleComponent
import com.komshop.ui.viewmodel.AuctionItemViewModel
import com.komshop.ui.viewmodel.BidingViewModel
import com.komshop.util.CodeGeneratorHelper
import com.komshop.util.ShopUtil.getPageTitle
import kotlinx.coroutines.launch

@Composable
fun AuctionItemsSelection(
    navController: NavHostController,
    biddingViewModel: BidingViewModel,
) {
    val auctionItemViewModel: AuctionItemViewModel = hiltViewModel()
    val state = auctionItemViewModel.state
    val coroutineScope = rememberCoroutineScope()
    val preBidDialog = remember { mutableStateOf(false) }

    var previewItem by remember { mutableStateOf<AuctionItem?>(null) }
    val showBidDialog = remember { mutableStateOf(false) }

    val loadingState = auctionItemViewModel.loadingEvent.collectAsState(initial = null).value
    val placeBidEventState = auctionItemViewModel.placeBidEvent.collectAsState(initial = null).value

    val showDialog = remember { mutableStateOf(false) }
    val bitMap = remember {
        CodeGeneratorHelper.generateQRCode(content = "1234567")
    }

    LaunchedEffect(key1 = true) {
        auctionItemViewModel.event(AuctionItemEvents.OnLoadItems)
    }

    PageBackground(
        topBar = {
            TopNav(
                navController = navController,
                title = "Shop",
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = { showDialog.value = true }) {
                Icon(
                    imageVector = Icons.Default.QrCode2,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(text = "My Code")
            }
        }
    ) {
        GridContainerComponent(
            res = loadingState,
            isEmpty = state.auctionItems.isEmpty(),
            onRefresh = { auctionItemViewModel.event(AuctionItemEvents.OnLoadItems) }) {

            items(state.auctionItems) { item ->
                SaleComponent(
                    item,
                    isAuction = false,
                    addToCart = {
                        biddingViewModel.event(BiddingEvents.OnAddToCart(item))
                    },
                    placeABid = {
                        previewItem = item
                        preBidDialog.value = true
                    }
                ) {
                    currentAuctionItem.value = item
                    coroutineScope.launch {
                        navController.navigate(Screen.ProductDetails.route)
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
        QRcodePreview(showDialog = showDialog, title = "My Code", bitmap = bitMap?.asImageBitmap())
//        LoadingDialog(res = loadingState)
        LoadingDialog(res = placeBidEventState, successMessage = "Your prebid have been placed.")
        // TESTING section
        previewItem?.let {
            PrebidDialog(auctionItem = it, preBidDialog, biddingViewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAuctionItem(auction: AuctionItem, onPreBid: () -> Unit, onClick: () -> Unit = {}) {
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
                CoilImage(
                    imageModel = auction.image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = auction.name,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
                Text(
                    text = "Bid: MK ${formatMoney(auction.currentBid)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                )

                Button(onClick = { onPreBid() }) {
                    Text(text = "Place A bid")
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QRcodePreview(
    showDialog: MutableState<Boolean> = mutableStateOf(false),
    title: String,
    bitmap: ImageBitmap?,
) {
    if (showDialog.value) {
        AlertDialog(onDismissRequest = { showDialog.value = false }) {
            Card(modifier = Modifier.width(300.dp)) {
                Box(modifier = Modifier) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(color = MaterialTheme.colorScheme.primary) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = { showDialog.value = false }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = null
                                    )
                                }
                                Text(text = title)
                            }
                        }

                        if (bitmap != null) {
                            Image(
                                bitmap = bitmap,
                                contentDescription = null,
                                modifier = Modifier.size(300.dp),
//                                contentScale = ContentScale.Crop
                            )
                        }

                    }
                }
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PlaceABid(showDialog: MutableState<Boolean> = mutableStateOf(false), item: AuctionItem) {
    val coroutinScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val bringIntoViewRequester = BringIntoViewRequester()

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            modifier = Modifier.imePadding()
        ) {

        }
    }
}