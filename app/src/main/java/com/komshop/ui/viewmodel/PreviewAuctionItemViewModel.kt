package com.komshop.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.komshop.data.repo.AuctionsRepo
import com.komshop.ui.events.AuctionItemPreviewEvent
import com.komshop.ui.pages.bid.presentation.AuctionSelectectionUiState
import com.komshop.ui.usecases.Validators
import com.komshop.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class PreviewAuctionItemViewModel @Inject constructor(
    private val validators: Validators,
    private val auctionsRepo: AuctionsRepo
): ViewModel() {
    var state by mutableStateOf(AuctionSelectectionUiState())
    private val loadingEventChannel = Channel<Resource<Any>>()
    val loadingEvent = loadingEventChannel.receiveAsFlow()

    fun event(event: AuctionItemPreviewEvent) {
        when (event) {
            is AuctionItemPreviewEvent.OnSetCurrentItem -> {
                state = state.copy(currentItem = event.item)
            }
            is AuctionItemPreviewEvent.OnPlaceBid -> {
//                state = state.copy(bidAmount = event.amount)
            }

            is AuctionItemPreviewEvent.OnSubmit -> {
//                placeBid()
            }
        }
    }

}