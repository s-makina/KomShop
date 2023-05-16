package com.komshop.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komshop.data.repo.AuctionsRepo
import com.komshop.enam.AuctionTypes
import com.komshop.ui.events.AuctionEvent
import com.komshop.ui.pages.bid.presentation.AuctionSelectectionUiState
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
class AuctionSelectionViewModel @Inject constructor(
    private val auctionsRepo: AuctionsRepo
) : ViewModel() {
    var state by mutableStateOf(AuctionSelectectionUiState())
    private val loadingEventChannel = Channel<Resource<Any>>()
    val loadingEvent = loadingEventChannel.receiveAsFlow()

    fun event(event: AuctionEvent) {
        when (event) {
            is AuctionEvent.OnLoadAuctions -> {
                loadAuctions(event.auctionType)
            }
        }
    }

    private fun loadAuctions(type: AuctionTypes) {
        viewModelScope.launch(Dispatchers.IO) {
            auctionsRepo.getAuctions(type.title).collectLatest {
                if (it.status == Status.SUCCESS) {
                    state = state.copy(auctions = it.data ?: emptyList())
                }
                loadingEventChannel.send(it)
            }
        }
    }
}