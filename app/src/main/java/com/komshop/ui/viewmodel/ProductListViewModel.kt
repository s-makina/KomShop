package com.komshop.ui.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komshop.data.paginator.DefaultPaginator
import com.komshop.data.repo.AuctionsRepo
import com.komshop.ui.events.ProductListEvents
import com.komshop.ui.pages.bid.presentation.ProductListState
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
class ProductListViewModel @Inject constructor(
    private val auctionsRepo: AuctionsRepo
): ViewModel(){
    var state by mutableStateOf(ProductListState())
    private val loadingEventChannel = Channel<Resource<Any>>()
    val loadingEvent = loadingEventChannel.receiveAsFlow()

    private val placeBidEventChannel = Channel<Resource<Any>>()
    val placeBidEvent = placeBidEventChannel.receiveAsFlow()

    lateinit var context: Context

    private val paginator = DefaultPaginator(
        initialKey = state.page,
        onLoadUpdated = {
            state = state.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            auctionsRepo.getItemsPaginated(page = nextPage, pageSize = 10, state.searchTerm, categoryId = state.categoryId)
        },
        getNextKey = {
            state.page + 1
        },
        onError = {
            state = state.copy(error = it?.message)
        },
        onSuccess = { items, newKey ->
            state = state.copy(
                products = state.products + items,
                page = newKey,
                endReached = items.isEmpty()
            )
        }
    )

    fun init(context: Context) {
        this.context = context
    }
    fun loadNextItems() {
        viewModelScope.launch(Dispatchers.IO) {
            paginator.loadNextItems()
        }
    }

    fun event(event: ProductListEvents) {
        when (event) {
            is ProductListEvents.OnLoadMyBids -> {
//                loadNextItems()
            }
            is ProductListEvents.OnLoadItems -> {
                loadNextItems()
            }

            is ProductListEvents.OnBid -> {
//                placeAbid(event.auctionItem)
            }

            is ProductListEvents.OnFilterCategory -> {
                paginator.reset()
                state = state.copy(categoryId = event.id, products = emptyList())
                loadNextItems()
            }

            is ProductListEvents.SubmitSearch -> {
                paginator.reset()
                state = state.copy(products = emptyList())
                loadNextItems()
            }

            is ProductListEvents.OnSearch -> {
                state = state.copy(searchTerm = event.searchTerm)
            }

            is ProductListEvents.OnLoadCategories -> {
                loadCategories()
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            auctionsRepo.getProductCategories().collectLatest {
                if (it.status == Status.SUCCESS)
                    state = state.copy(productCategories = it.data ?: emptyList())
                loadingEventChannel.send(it)
            }
        }
    }

    private fun loadMyBids() {
        viewModelScope.launch(Dispatchers.IO) {
            auctionsRepo.getMyBids().collectLatest {
                if (it.status == Status.SUCCESS) {
//                    state = state.copy(products = it.data ?: emptyList())
                }
                loadingEventChannel.send(it)
            }
        }
    }

    private fun loadItems() {
//        viewModelScope.launch(Dispatchers.IO) {
//            auctionsRepo.getProducts().collectLatest {
//                if (it.status == Status.SUCCESS) {
//                    state = state.copy(products = it.data ?: emptyList())
//                }
//                loadingEventChannel.send(it)
//            }
//        }
    }
}