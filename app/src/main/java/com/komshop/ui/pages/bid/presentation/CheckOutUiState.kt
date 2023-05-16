package com.komshop.ui.pages.bid.presentation

import com.komshop.data.model.CartItem

data class CheckOutUiState(
    val items: List<CartItem> = emptyList(),
    val totalPrice: Double = 0.0,
    val name: String = "",
    val nameError: String? = null,
    val address: String = "",
    val addressError: String? = null,
    val phone: String = "",
    val phoneError: String? = null,
    val alternativePhone: String = "",
    val email: String = "",
    val emailError: String? = null
)