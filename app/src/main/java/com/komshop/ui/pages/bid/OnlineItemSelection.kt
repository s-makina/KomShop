package com.komshop.ui.pages.bid

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.komshop.R
import com.komshop.data.Session
import com.komshop.ui.componets.TopNav
import com.komshop.ui.pages.shop.QRcodePreview
import com.komshop.ui.theme.orangeBg
import com.komshop.ui.viewmodel.ProductListViewModel
import com.komshop.util.CodeGeneratorHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnlineBidPage(navController: NavHostController) {

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
//        auctionItemViewModel.event(AuctionItemEvents.OnLoadItems(auctionId = ))
    }

//    ChangeStatusBarColor(bgColor)
    Scaffold(
        topBar = {
            TopNav(
                navController = navController,
                title = Session.selectedAuction.value?.name ?: "",
                subtitle = "Online Auction"
            )
        },
        modifier = Modifier.fillMaxSize(),
        containerColor = orangeBg,
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
    ) { innerPadding ->

        Box(modifier = Modifier.fillMaxSize()) {
            Icon(
                painter = painterResource(id = R.drawable.ic_decal2),
                contentDescription = null,
                modifier = Modifier.align(
                    Alignment.BottomEnd
                ),
                tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
            )
        }

        Column(modifier = Modifier.padding(paddingValues = innerPadding)) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(end = 8.dp, top = 8.dp)
            ) {
//                items(state.auctionItems) { item ->
//                    MyAuctionItem(item, onPreBid = {}) {
//                        Session.currentAuctionItem.value = item
//                        coroutineScope.launch {
//                            navController.navigate(Screen.OnlineBidPreview.route)
//                        }
////                        showBidDialog.value = true
////                        auctionItemViewModel.event(AuctionItemEvents.OnBid(item))
//                    }
//                }

                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }

        QRcodePreview(showDialog = showDialog, title = "My Code", bitmap = bitMap?.asImageBitmap())
        LoadingDialog(res = loadingState)
    }

}