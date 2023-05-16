package com.komshop.ui.pages.bid.presentation

import com.komshop.data.model.AuctionItem

data class AuctionItemState(
    val selectedAuction: com.komshop.data.Auction? = null,
    val auctionItems: List<AuctionItem> = emptyList()
)