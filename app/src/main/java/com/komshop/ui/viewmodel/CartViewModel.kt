package com.komshop.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komshop.data.model.CartItem
import com.komshop.data.repo.CartRepo
import com.komshop.log
import com.komshop.ui.events.CartEvents
import com.komshop.ui.pages.bid.presentation.CartUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    val cartRepo: CartRepo,
) : ViewModel() {
    var state by mutableStateOf(CartUiState())

    fun event(event: CartEvents) {
        when (event) {
            is CartEvents.OnLoadItems -> {
                loadItems()
            }

            is CartEvents.RemoveItem -> {
                removeItem(event.item)
            }

            is CartEvents.AddQty -> {
                addQty(event.item)
            }

            is CartEvents.ReduceQty -> {
                reduceQty(event.item)
            }
        }
    }

    private fun reduceQty(item: CartItem) {
        if (item.quantity <= 1) return
        viewModelScope.launch(Dispatchers.IO) {
            val itm =  item.copy(quantity = item.quantity - 1)
            cartRepo.updateItem(itm)
        }
    }

    private fun addQty(item: CartItem) {
        log("ADDEDDDDD")
        viewModelScope.launch(Dispatchers.IO) {
            val itm =  item.copy(quantity = item.quantity+1)
            cartRepo.updateItem(itm)
        }
    }

    private fun loadItems() {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepo.getCartItems().collectLatest {
                state = state.copy(items = it, totalAmount = it.sumOf { (it.price * it.quantity).toDouble() })
            }
        }
    }

    private fun removeItem(item: CartItem) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepo.removeItem(item)
        }
    }
}