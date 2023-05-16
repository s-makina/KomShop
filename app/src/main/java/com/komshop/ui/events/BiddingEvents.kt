package com.komshop.ui.events

import com.komshop.data.model.AuctionItem

sealed class BiddingEvents {
    class OnAddToCart(val item: AuctionItem): BiddingEvents()
    class OnBidAmountChange(val amount: String): BiddingEvents()
    class OnSubmitBid(val item: AuctionItem): BiddingEvents()
}