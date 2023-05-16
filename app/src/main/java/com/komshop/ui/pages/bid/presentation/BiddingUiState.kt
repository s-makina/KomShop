package com.komshop.ui.pages.bid.presentation

import com.komshop.data.model.AuctionItem

data class BiddingUiState(
    val product: AuctionItem? = null,
    val bidAmount: String = "",
    val bidAmountError: String? = null,
)