package com.komshop.ui.pages.bid.presentation

import com.komshop.data.model.AuctionItem
import com.komshop.data.model.AuctionType

data class DailySalesState(
    val auctionTypes: List<AuctionType> = emptyList(),
    val itemList: List<AuctionItem> = emptyList()
)