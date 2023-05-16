package com.komshop.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komshop.data.repo.CartRepo
import com.komshop.ui.events.CheckOutEvent
import com.komshop.ui.pages.bid.presentation.CheckOutUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckOutViewModel @Inject constructor(
    private val cartRepo: CartRepo
): ViewModel() {
    var state by mutableStateOf(CheckOutUiState())

    fun event( event: CheckOutEvent) {
        when (event) {
            is CheckOutEvent.Onload -> {
                loadItems()
            }
        }
    }

    private fun loadItems() {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepo.getCartItems().collectLatest {
                state = state.copy(items = it, it.sumOf { it.totalPrice })
            }
        }
    }
}