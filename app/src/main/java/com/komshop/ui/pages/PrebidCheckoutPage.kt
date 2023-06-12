package com.komshop.ui.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.skydoves.landscapist.coil.CoilImage
import com.komshop.data.Session.currentAuctionItem
import com.komshop.ui.componets.TopNav
import com.komshop.ui.events.AuctionItemPreviewEvent
import com.komshop.ui.pages.bid.LoadingDialog
import com.komshop.ui.theme.onBlue
import com.komshop.ui.viewmodel.PreviewAuctionItemViewModel

//@Deprecated("We have a new Preview Ui ProductDetails")
//@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
//@Composable
//fun PreviewAuctionItem(navController: NavHostController) {
//
//    val previewAuctionType: PreviewAuctionItemViewModel = hiltViewModel()
//    val state = previewAuctionType.state
//
//    val item = currentAuctionItem.value ?: return
//    val coroutinScope = rememberCoroutineScope()
//    val focusManager = LocalFocusManager.current
//    val bringIntoViewRequester = BringIntoViewRequester()
//    val loadingState = previewAuctionType.loadingEvent.collectAsState(initial = null).value
//
//    LaunchedEffect(key1 = true) {
//        previewAuctionType.event(AuctionItemPreviewEvent.OnSetCurrentItem(item))
//    }
//
//    Scaffold(modifier = Modifier.fillMaxWidth(), topBar = {
//        TopNav(
//            navController = navController,
//            title = item.name
//        )
//    }, containerColor = MaterialTheme.colorScheme.primary) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//        ) {
//            Box(modifier = Modifier.fillMaxWidth()) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .verticalScroll(rememberScrollState())
//                ) {
//                    CoilImage(
//                        imageModel = item, modifier = Modifier
//                            .fillMaxWidth()
////                            .height(300.dp)
//                    )
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(8.dp)
//                    ) {
//                        Text(
//                            text = "Current bid: ${item.currentBid}",
//                            style = MaterialTheme.typography.bodyMedium,
//                            color = onBlue,
//                            maxLines = 1,
//                        )
//
//                        Text(
//                            text = item.name,
//                            modifier = Modifier.fillMaxWidth(),
//                            style = MaterialTheme.typography.titleLarge
//                        )
//                        Text(
//                            text = item.description ?: "",
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(bottom = 8.dp),
//                            style = MaterialTheme.typography.bodyMedium
//                        )
//
////                        OutlinedTextField(
////                            colors = TextFieldDefaults.outlinedTextFieldColors(
////                                textColor = MaterialTheme.colorScheme.onPrimary,
////                                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
////                                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
////                                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
////                                placeholderColor = MaterialTheme.colorScheme.onPrimary,
////                                disabledPlaceholderColor = MaterialTheme.colorScheme.onPrimary,
////                                unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
////                            ),
////                            value = state.
////                            bidAmount,
////                            onValueChange = {
////                                previewAuctionType.event(AuctionItemPreviewEvent.OnPlaceBid(it))
////                            },
////                            label = { Text(text = "Pre Bid Amount") },
////                            modifier = Modifier
////                                .fillMaxWidth()
////                                .padding(bottom = 8.dp)
////                                .onFocusEvent { event ->
////                                    if (event.isFocused) {
////                                        coroutinScope.launch {
////                                            bringIntoViewRequester.bringIntoView()
////                                        }
////                                    }
////                                },
////                            isError = state.bidAmountError != null,
////                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
////                        )
//
//                        Button(
//                            onClick = { previewAuctionType.event(AuctionItemPreviewEvent.OnSubmit) },
//                            modifier = Modifier
//                                .bringIntoViewRequester(bringIntoViewRequester),
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = MaterialTheme.colorScheme.secondary,
//                                contentColor = MaterialTheme.colorScheme.onSecondary
//                            )
//                        ) {
//                            Text(text = "Place your bid")
//                        }
//                    }
//                }
//            }
//        }
//
//        LoadingDialog(res = loadingState, successMessage = "Your bid have been placed")
//    }
//}