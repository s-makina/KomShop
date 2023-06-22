package com.komshop.ui.pages.bid.presentation

import com.komshop.data.model.CartItem

data class CheckOutUiState(
    val items: List<CartItem> = emptyList(),
    val totalPrice: Double = 0.0,
    val firstName: String = "",
    val firstNameError: String? = null,
    val lastName: String = "",
    val lastNameError: String? = null,
    val city: String = "",
    val cityError: String? = null,
    val phone: String = "",
    val phoneError: String? = null,
)