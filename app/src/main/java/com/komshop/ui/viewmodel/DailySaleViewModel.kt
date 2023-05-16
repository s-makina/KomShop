package com.komshop.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komshop.data.repo.AuctionsRepo
import com.komshop.data.repo.SalesRepo
import com.komshop.ui.events.SaleEvents
import com.komshop.ui.pages.bid.presentation.DailySalesState
import com.komshop.util.Resource
import com.komshop.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailySaleViewModel @Inject constructor(
    private val salesRepo: SalesRepo,
    private val auctionsRepo: AuctionsRepo
): ViewModel() {
    private val _loadingData = MutableStateFlow<Resource<Any>>(Resource.idle<Any>())
    val loadingState = _loadingData.asStateFlow()
    var state by mutableStateOf(DailySalesState())

    fun event(event: SaleEvents) {
        when (event) {
            is SaleEvents.OnLoadData -> {

            }
            is SaleEvents.OnLoadAuctionTypes -> {

            }
        }
    }

    private fun loadAuctions(type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            auctionsRepo.getAuctions(type).collectLatest {
                if (it.status == Status.SUCCESS) {
                    state = state.copy(auctionTypes = it.data ?: emptyList())
                }
                _loadingData.emit(it)
            }
        }
    }

    private fun getDailySales() {
        viewModelScope.launch(Dispatchers.IO) {

        }
    }
}