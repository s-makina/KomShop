package com.komshop.ui.pages.bid.presentation

import com.komshop.data.model.AuctionItem
import com.komshop.data.model.AuctionType

data class AuctionSelectectionUiState(
    val auctions: List<AuctionType> = emptyList(),
    val currentItem: AuctionItem? = null,
)