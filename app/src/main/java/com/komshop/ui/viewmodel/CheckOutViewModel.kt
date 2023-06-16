package com.komshop.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komshop.data.getTotalPrice
import com.komshop.data.model.CartItem
import com.komshop.data.model.Product
import com.komshop.data.repo.CartRepo
import com.komshop.formatMoney
import com.komshop.ui.events.CheckOutEvent
import com.komshop.ui.pages.bid.presentation.CheckOutUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckOutViewModel @Inject constructor(
    private val cartRepo: CartRepo,
) : ViewModel() {
    var state by mutableStateOf(CheckOutUiState())

    fun event(event: CheckOutEvent) {
        when (event) {
            is CheckOutEvent.Onload -> {
                loadItems()
            }

            is CheckOutEvent.OnFirstNameChange -> {

            }

            is CheckOutEvent.OnLastNameChange -> {

            }

            is CheckOutEvent.OnPhoneChange -> {

            }

            is CheckOutEvent.OnSubmit -> {

            }
        }
    }

    private fun loadItems() {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepo.getCartItems().collectLatest {
                state = state.copy(items = it, it.sumOf { it.getTotalPrice() })
            }
        }
    }

    fun getMsg(): String {
        var s = state.items.joinToString("\n") { getIt(it) }
        s += "*Grand Total: MK ${formatMoney(state.items.sumOf { it.getTotalPrice() })}*"
        return s
    }

    private fun getIt(item: CartItem): String {
        return """
            *Product:* ${item.name}, %0a
            *Quantity:* ${item.quantity} , %0a
            *Price:* ${formatMoney(item.price.toDouble())}, %0a
            *TotalPrice:* ${formatMoney(item.getTotalPrice())} %0a
            *Link:* ${item.permalink} %0a
            -------------------- %0a
        """.trimIndent()
    }
}