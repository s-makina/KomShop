package com.komshop.ui.pages.shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.komshop.data.Session.currentAuctionItem
import com.skydoves.landscapist.coil.CoilImage
import com.komshop.data.model.Product
import com.komshop.formatMoney
import com.komshop.navigation.Screen
import com.komshop.ui.componets.GridContainerComponent
import com.komshop.ui.componets.PageBackground
import com.komshop.ui.componets.ProgressLoader
import com.komshop.ui.componets.SearchTextField
import com.komshop.ui.componets.TopNav
import com.komshop.ui.events.BiddingEvents
import com.komshop.ui.events.ProductListEvents
import com.komshop.ui.viewmodel.ProductListViewModel
import com.komshop.ui.viewmodel.BidingViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProductListPage(
    navController: NavHostController,
    biddingViewModel: BidingViewModel,
) {
    val productListViewModel: ProductListViewModel = hiltViewModel()
    val state = productListViewModel.state
    val coroutineScope = rememberCoroutineScope()
    val preBidDialog = remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    val loadingState = productListViewModel.loadingEvent.collectAsState(initial = null).value
    val placeBidEventState = productListViewModel.placeBidEvent.collectAsState(initial = null).value

    LaunchedEffect(key1 = true) {
        productListViewModel.event(ProductListEvents.OnLoadCategories)
        productListViewModel.event(ProductListEvents.OnLoadItems)
    }

    PageBackground(
        topBar = {
            TopNav(
                navController = navController,
                title = "",
//                searchIcon = true
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                productListViewModel.event(ProductListEvents.OnLoadCategories)
                productListViewModel.event(ProductListEvents.SubmitSearch)
            }) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
            }
        }
    ) {
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 4.dp, top = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchTextField(
                    trailingIcon = null,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    placeholderText = "Search for products",
                    value = state.searchTerm,
                    onTextChange = {
                        productListViewModel.event(ProductListEvents.OnSearch(it))
                    },
                    onSearch = {
                        productListViewModel.event(ProductListEvents.SubmitSearch)
                        keyboardController?.hide()
                    }
                )
                FilledIconButton(onClick = {
                    productListViewModel.event(ProductListEvents.SubmitSearch)
                    keyboardController?.hide()
                }) {
                    Icon(
                        Icons.Filled.Search,
                        null,
                        tint = LocalContentColor.current.copy(alpha = 0.4f),
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }
            }

            Row(
                Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Spacer(modifier = Modifier.width(4.dp))
                FilterChip(
                    selected = state.categoryId == "",
                    onClick = { productListViewModel.event(ProductListEvents.OnFilterCategory("")) },
                    label = {
                        Text(text = "All")
                    })
                state.productCategories.forEach { cate ->
                    FilterChip(
                        selected = state.categoryId == cate.id,
                        onClick = {
                            productListViewModel.event(
                                ProductListEvents.OnFilterCategory(
                                    cate.id
                                )
                            )
                        },
                        label = {
                            Text(text = cate.name)
                        })
                }
                Spacer(modifier = Modifier.width(8.dp))
            }

            GridContainerComponent(
                isLoading = state.isLoading,
                isEmpty = state.products.isEmpty(),
                onRefresh = { productListViewModel.event(ProductListEvents.OnLoadItems) }) {

                items(state.products.size) { i ->
                    val product = state.products[i]
                    if (i >= state.products.size - 1 && !state.endReached && !state.isLoading) {
                        productListViewModel.loadNextItems()
                    }

                    ProductComponent(
                        product,
                        addToCart = {
                            biddingViewModel.event(BiddingEvents.OnAddToCart(product))
                        }
                    ) {
                        currentAuctionItem.value = product
                        coroutineScope.launch {
                            navController.navigate(Screen.ProductDetails.route)
                        }
                    }
                }

                item {
                    if (state.isLoading) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            ProgressLoader(modifier = Modifier.size(150.dp))
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(150.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAuctionItem(auction: Product, onPreBid: () -> Unit, onClick: () -> Unit = {}) {
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
                    imageModel = auction.images.firstOrNull() ?: "",
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
                    text = "Bid: MK ${formatMoney(auction.salePrice.toDouble())}",
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