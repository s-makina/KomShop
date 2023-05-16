package com.komshop.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komshop.data.repo.AuctionsRepo
import com.komshop.ui.events.AuctionItemEvents
import com.komshop.ui.pages.bid.presentation.AuctionItemState
import com.komshop.util.Resource
import com.komshop.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuctionItemViewModel @Inject constructor(
    private val auctionsRepo: AuctionsRepo
): ViewModel(){
    var state by mutableStateOf(AuctionItemState())
    private val loadingEventChannel = Channel<Resource<Any>>()
    val loadingEvent = loadingEventChannel.receiveAsFlow()

    private val placeBidEventChannel = Channel<Resource<Any>>()
    val placeBidEvent = placeBidEventChannel.receiveAsFlow()

    fun event(event: AuctionItemEvents) {
        when (event) {
            is AuctionItemEvents.OnLoadMyBids -> {
                loadMyBids()
            }
            is AuctionItemEvents.OnLoadItems -> {
                loadItems()
            }

            is AuctionItemEvents.OnBid -> {
//                placeAbid(event.auctionItem)
            }
        }
    }

    private fun loadMyBids() {
        viewModelScope.launch(Dispatchers.IO) {
            auctionsRepo.getMyBids().collectLatest {
                if (it.status == Status.SUCCESS) {
                    state = state.copy(auctionItems = it.data ?: emptyList())
                }
                loadingEventChannel.send(it)
            }
        }
    }

    private fun loadItems() {
        viewModelScope.launch(Dispatchers.IO) {
            auctionsRepo.getItems().collectLatest {
                if (it.status == Status.SUCCESS) {
                    state = state.copy(auctionItems = it.data ?: emptyList())
                }
                loadingEventChannel.send(it)
            }
        }
    }


//    private fun placeAbid(auctionItem: AuctionItem) {
//        viewModelScope.launch(Dispatchers.IO) {
//            auctionsRepo.placeAbid(auctionItem).collectLatest {
//                placeBidEventChannel.send(it)
//            }
//        }
//    }
}