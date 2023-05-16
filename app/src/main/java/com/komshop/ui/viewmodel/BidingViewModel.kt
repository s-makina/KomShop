package com.komshop.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komshop.data.model.AuctionItem
import com.komshop.data.repo.BidingRepo
import com.komshop.data.room.toCartEntity
import com.komshop.ui.events.BiddingEvents
import com.komshop.ui.pages.bid.presentation.BiddingUiState
import com.komshop.ui.usecases.Validators
import com.komshop.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BidingViewModel @Inject constructor(
    private val validators: Validators,
    private val bidingRepo: BidingRepo,
) : ViewModel() {
    var state by mutableStateOf(BiddingUiState())
    private val loadingEventChannel = Channel<Resource<Any>>()
    val loadingEvent = loadingEventChannel.receiveAsFlow()

    fun event(event: BiddingEvents) {
        when (event) {
            is BiddingEvents.OnBidAmountChange -> {
                state = state.copy(bidAmount = event.amount)
                validate()
            }
            is BiddingEvents.OnSubmitBid -> {
                state = state.copy(product = event.item)
                placeBid()
            }

            is BiddingEvents.OnAddToCart -> {
                addToCart(event.item)
            }
        }
    }

    private fun addToCart(auctionItem: AuctionItem) {
        viewModelScope.launch(Dispatchers.IO) {
            bidingRepo.addToCart(auctionItem = auctionItem.toCartEntity())
        }
    }

    private fun validate(): Boolean {
        val result = validators.validateRequired.execute(state.bidAmount)

        val hasErrors = listOf(
            result,
        ).any { !it.successful }

        state = state.copy(bidAmountError = result.errorMessage)
        return hasErrors
    }

    private fun placeBid() {
        val item = state.product ?: return
        if (validate()) {
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            bidingRepo.placeAbid(
                itemId = item.id,
                bidAmount = state.bidAmount
            ).collectLatest {
                loadingEventChannel.send(it)
            }
            state = state.copy(product = null, bidAmount = "", bidAmountError = null)
        }
    }
}