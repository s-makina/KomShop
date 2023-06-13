package com.komshop.ui.pages.shop

import android.text.Html
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.komshop.data.model.Product
import com.komshop.ui.componets.PageBackground
import com.komshop.ui.componets.TopNav
import com.komshop.ui.dialog.ImagePreviewDialog
import com.komshop.ui.dialog.getImageRequest
import com.komshop.ui.events.BiddingEvents
import com.komshop.ui.viewmodel.BidingViewModel

@Composable
fun ProductDetails(
    navController: NavHostController,
    product: Product,
    biddingViewModel: BidingViewModel,
) {
    val previewImage = remember { mutableStateOf("") }
    val showPreviewImageDialog = remember { mutableStateOf(false) }

//    var previewItem by remember { mutableStateOf<AuctionItem?>(null) }
    val preBidDialog = remember { mutableStateOf(false) }

    PageBackground(
        modifier = Modifier,
        topBar = { TopNav(navController = navController, title = "", backButton = true) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Text(text = "Add to cart")
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = null
                    )
                },
                onClick = {
                    biddingViewModel.event(BiddingEvents.OnAddToCart(product))
                })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clickable {
                        previewImage.value = product.images.firstOrNull()?.src ?: ""
                        showPreviewImageDialog.value = true
                    }
            ) {
                AsyncImage(
                    model = getImageRequest(product.images.firstOrNull()?.src ?: ""),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = null
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-25).dp)
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = product.name, style = MaterialTheme.typography.titleLarge)
                    Text(
                        text = Html.fromHtml(product.priceHtml).toString(),//currentBidFormat(product.currentBid),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 0.dp)
                    )
                }
            }
            if (product.images.size > 1) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = -(15).dp)
                        .padding(top = 0.dp, start = 8.dp, end = 8.dp),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    LazyRow(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(product.images) { item ->
                            Box(modifier = Modifier
                                .size(100.dp)
                                .clickable {
                                    previewImage.value = item.src
                                    showPreviewImageDialog.value = true
                                }) {
                                AsyncImage(
                                    model = getImageRequest(url = item.src),
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = -(5).dp)
                    .padding(top = 0.dp, start = 8.dp, end = 8.dp),
                shape = RoundedCornerShape(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = Html.fromHtml(product.description).toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
        }

        ImagePreviewDialog(previewImage.value, showPreviewImageDialog)
//        PrebidDialog(auctionItem = product, preBidDialog, biddingViewModel)
    }
}


