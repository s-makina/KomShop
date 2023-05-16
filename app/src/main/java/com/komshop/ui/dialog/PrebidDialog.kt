package com.komshop.ui.dialog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.skydoves.landscapist.coil.CoilImage
import com.komshop.data.model.AuctionItem
import com.komshop.formatMoney
import com.komshop.log
import com.komshop.ui.events.BiddingEvents
import com.komshop.ui.viewmodel.BidingViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PrebidDialog(
    auctionItem: AuctionItem,
    showDialog: MutableState<Boolean>,
    biddingViewModel: BidingViewModel,
) {
    val state = biddingViewModel.state
    val focusRequester = remember {
        FocusRequester()
    }
    var bidAmount by remember { mutableStateOf("") }

    if (showDialog.value) {
        Dialog(onDismissRequest = {
            showDialog.value = false
        }) {
            Card(modifier = Modifier) {
                CoilImage(
                    imageModel = auctionItem.image,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = auctionItem.name,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding()
                    )
                    Text(
                        text = "Current Bid: MK " + formatMoney(auctionItem.currentBid),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                    OutlinedTextField(
                        value = state.bidAmount,
                        onValueChange = {
                            biddingViewModel.event(BiddingEvents.OnBidAmountChange(it))
                        },
//                        placeholder = { Text(text = "Current bid: MK ${formatMoney(auctionItem.currentBid)}") },
                        isError = state.bidAmountError != null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        label = { Text(text = "Bid Amount") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )
                }
                Divider()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.End,
                ) {
                    OutlinedButton(
                        onClick = { showDialog.value = false },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(text = "Close")
                    }
                    Button(onClick = {
                        biddingViewModel.event(BiddingEvents.OnSubmitBid(item = auctionItem))
                        showDialog.value = false
                    }
                    ) {
                        Text(text = "Submit")
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = auctionItem) {
        delay(200)
        try {
            focusRequester.requestFocus()
        } catch (e: Exception) {
            log(e.message)
        }
    }
}