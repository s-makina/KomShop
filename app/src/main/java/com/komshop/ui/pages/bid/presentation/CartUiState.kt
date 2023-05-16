package com.komshop.ui.pages.bid.presentation

import com.komshop.data.model.CartItem

data class CartUiState(
    val items: List<CartItem> = emptyList(),
    val totalAmount: Double = 0.0
)